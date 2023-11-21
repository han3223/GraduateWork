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
import com.example.documentsearch.api.apiRequests.MessageRequests
import com.example.documentsearch.api.apiRequests.MessengersRequests
import com.example.documentsearch.api.apiRequests.ProfilesRequests
import com.example.documentsearch.api.apiRequests.TagsRequests
import com.example.documentsearch.dataClasses.AnotherUser
import com.example.documentsearch.dataClasses.Messenger
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.navbar.NavigationScreens
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi


class MainActivity : ComponentActivity() {
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
    @OptIn(ExperimentalSerializationApi::class)
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
            val preferencesManager = PreferencesManager(context)

            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }

            // Контроллер для навигации
            val navController = rememberNavController()

            // Обработчик back жеста
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigateUp()
                }
            }
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

            // TODO(Пользователь должен быть получен из базы данных)
            var profile by remember { mutableStateOf<Profile?>(null) }

            // TODO(Переписки должны быть получены из базы данных)
            var listMessenger by remember { mutableStateOf<MutableList<Messenger>>(mutableListOf()) }

            var users by remember { mutableStateOf<List<AnotherUser>?>(null) }
            CoroutineScope(Dispatchers.Main).launch {
                val signInProfile: List<AnotherUser>? =
                    ProfilesRequests().getAllProfileWithoutPassword()

                if (signInProfile != null) {
                    users = signInProfile
                }
            }

            val email = preferencesManager.getData(emailKeyPreferences)
            val password = preferencesManager.getData(passwordKeyPreferences)
            if (email != null && password != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    val signInProfile: Profile? = ProfilesRequests().getProfile(email, password)

                    if (signInProfile != null) {
                        profile = signInProfile
                        println("Это профиль $profile")
                        val getMessenger = MessengersRequests().getAllMessengersFromUser(profile!!.id!!)
                        getMessenger?.forEach {
                            val anotherUser = ProfilesRequests().getAnotherProfileById(it.interlocutor)
                            println("Это другой пользователь $anotherUser")
                            val listMessage = MessageRequests().getMessagesByMessenger(it.id)
                            println("Это переписка $listMessage")
                            if (anotherUser != null)
                                listMessenger.add(Messenger(it.id, anotherUser, listMessage))
                        }

                        println("Это полученный список переписок $listMessenger")
                    }
                }
            }

            // TODO(Документы должны быть получены из базы данных)
            val listDocumets = listDocumet

            // TODO(Теги должны быть получены из базы данных)
            val documentTags = TagsRequests().getDocumentTags()
            val profileTags = TagsRequests().getProfileTags()

            NavigationScreens(
                navController = navController,
                profile = profile,
                anotherUsers = users,
                onProfileChange = {
                    profile = it
                },
                listDocuments = listDocumets,
                tagsDocumentation = documentTags,
                tagsProfile = profileTags,
                listMessenger = listMessenger,
                onListMessenger = {
                    listMessenger = it
                }
            )
        }
    }
}
