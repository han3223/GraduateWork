package com.example.documentsearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.caverock.androidsvg.BuildConfig
import com.example.documentsearch.api.apiRequests.message.MessageRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.navbar.NavigationScreens
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
class MainActivity : ComponentActivity() {
    private var savedEmail: String? = null
    private var savedPassword: String? = null

    private val profileRequestService = ProfileRequestServicesImpl()
    private val messengerRequestService = MessengersRequestServicesImpl()
    private val messageRequestService = MessageRequestServicesImpl()
    private val tagRequestService = TagRequestServicesImpl()

    private var userProfile: ProfilePrototype? = null
    private var userMessengers = mutableListOf<MessengerPrototype>()
    private var allUsersProfile = listOf<AnotherUserPrototype>()
    private var profileTags = listOf<TagPrototype>()
    private var documentTags = listOf<TagPrototype>()
    private var documents = mutableListOf<DocumentWithPercentage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build()
            StrictMode.setThreadPolicy(policy)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            val context = LocalContext.current
            val systemUiController = rememberSystemUiController()
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

            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }

            getUserProfilePrototype()
            getMessengersPrototype()
            getAllUsersProfile()
            getProfileTags()
            getDocumentTags()
            getDocuments()

            NavigationScreens(
                navController = navigationController,
                profile = userProfile,
                anotherUsers = allUsersProfile,
                onProfileChange = {
                    userProfile = it
                },
                listDocuments = documents,
                tagsDocumentation = documentTags,
                tagsProfile = profileTags,
                listMessenger = userMessengers,
                onListMessenger = {
                    userMessengers = it
                }
            )
        }
    }

    private fun getUserProfilePrototype() {
        if (savedEmail != null && savedPassword != null) {
            userProfile = profileRequestService.getProfileUsingEmailAndPassword(savedEmail!!, savedPassword!!)
        }
    }

    private fun getMessengersPrototype() {
        if (userProfile != null) {
            val messengerPrototypeDataBase = messengerRequestService.getAllMessengersUsingUserId(userProfile!!.id!!)
            messengerPrototypeDataBase?.forEach { messenger ->
                val anotherUser = profileRequestService.getAnotherProfileUsingId(messenger.interlocutor)
                if (anotherUser != null) {
                    val messages = messageRequestService.getMessagesFromMessenger(messenger.id)
                    val messengerPrototype = MessengerPrototype(messenger.id, anotherUser, messages)
                    userMessengers.add(messengerPrototype)
                }
            }
        }
    }


    private fun getAllUsersProfile() {
        allUsersProfile = profileRequestService.getAllAnotherProfile()
    }


    private fun getProfileTags() {
        profileTags = tagRequestService.getProfileTags()
    }


    private fun getDocumentTags() {
        documentTags = tagRequestService.getDocumentTags()
    }

    private fun getDocuments() {
        documents = listDocumet
    }
}
