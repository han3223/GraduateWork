package com.example.documentsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.caverock.androidsvg.BuildConfig
import com.example.documentsearch.api.apiRequests.message.MessageRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.navbar.Navigation
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.DocumentWithPercentage
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
class MainActivity : ComponentActivity() {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    private var savedEmail: String? = null
    private var savedPassword: String? = null

    private val profileRequestService = ProfileRequestServicesImpl()
    private val messengerRequestService = MessengersRequestServicesImpl()
    private val messageRequestService = MessageRequestServicesImpl()
    private val tagRequestService = TagRequestServicesImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        if (BuildConfig.DEBUG) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        setContent {
            val context = LocalContext.current

            val systemUiController = rememberSystemUiController()
            SideEffect { systemUiController.setStatusBarColor(color = Color.Transparent) }

            val navigationController = rememberNavController()
            val onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigationController.navigateUp()
                }
            }
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

            val preferencesManager = PreferencesManager(context)
            savedEmail = preferencesManager.getData(emailKeyPreferences)
            savedPassword = preferencesManager.getData(passwordKeyPreferences)

            var userProfile by remember { mutableStateOf<UserProfilePrototype?>(null) }
            var userMessengers by remember { mutableStateOf(listOf<MessengerPrototype>()) }
            var allUsersProfiles by remember { mutableStateOf(listOf<AnotherUserProfilePrototype>()) }
            var profileTags by remember { mutableStateOf(listOf<TagPrototype>()) }
            var documentTags by remember { mutableStateOf(listOf<TagPrototype>()) }
            var documents by remember { mutableStateOf(listOf<DocumentWithPercentage>()) }

            coroutine.launch {
                userProfile = getUserProfilePrototype()
                if (userProfile != null)
                    userMessengers = getMessengersPrototype(userProfile!!)
                allUsersProfiles = getAllUsersProfile()
                profileTags = getProfileTags()
                documentTags = getDocumentTags()
                documents = getDocuments()
            }

            val navigation = Navigation(
                navigationController = navigationController,
                userProfile = userProfile,
                userMessengers = userMessengers,
                allUsersProfiles = allUsersProfiles,
                profileTags = profileTags,
                documentTags = documentTags,
                documents = documents
            )

            navigation.NavigationScreens(
                onProfileChange = { userProfile = it },
                onListMessenger = { userMessengers = it })
        }
    }

    private suspend fun getUserProfilePrototype(): UserProfilePrototype? {
        return if (savedEmail != null && savedPassword != null) {
            return coroutine.async {
                profileRequestService.getProfileUsingEmailAndPassword(savedEmail!!, savedPassword!!)
            }.await()
        } else
            null
    }

    private suspend fun getMessengersPrototype(userProfile: UserProfilePrototype): MutableList<MessengerPrototype> {
        val userMessengers = mutableListOf<MessengerPrototype>()
        coroutine.async {
            val messengerPrototypeDataBase =
                messengerRequestService.getAllMessengersUsingUserId(userProfile.id!!)
            messengerPrototypeDataBase?.forEach { messenger ->
                val anotherUser =
                    profileRequestService.getAnotherProfileUsingId(messenger.interlocutor)
                if (anotherUser != null) {
                    val messages = messageRequestService.getMessagesFromMessenger(messenger.id)
                    val messengerPrototype =
                        MessengerPrototype(messenger.id, anotherUser, messages)
                    userMessengers.add(messengerPrototype)
                }
            }
        }.await()

        return userMessengers
    }


    private suspend fun getAllUsersProfile(): List<AnotherUserProfilePrototype> {
        return coroutine.async {
            profileRequestService.getAllAnotherProfile()
        }.await()
    }


    private suspend fun getProfileTags(): List<TagPrototype> {
        return coroutine.async {
            tagRequestService.getProfileTags()
        }.await()
    }


    private suspend fun getDocumentTags(): List<TagPrototype> {
        return coroutine.async {
            tagRequestService.getDocumentTags()
        }.await()
    }

    private suspend fun getDocuments(): MutableList<DocumentWithPercentage> {
        return coroutine.async {
            listDocumet
        }.await()
    }
}
