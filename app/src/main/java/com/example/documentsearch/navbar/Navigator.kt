package com.example.documentsearch.navbar

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.documentsearch.Cache.CacheAllUsersProfile
import com.example.documentsearch.Cache.CacheDocumentTags
import com.example.documentsearch.Cache.CacheDocuments
import com.example.documentsearch.Cache.CacheProfileTags
import com.example.documentsearch.Cache.CacheUserMessengers
import com.example.documentsearch.Cache.CacheUserProfile
import com.example.documentsearch.api.apiRequests.document.DocumentRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.screens.profile.authenticationUser.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Navigator {
    private val cacheUserProfile = CacheUserProfile()
    private val cacheUserMessengers = CacheUserMessengers()
    private val cacheAllUsersProfile = CacheAllUsersProfile()
    private val cacheProfileTags = CacheProfileTags()
    private val cacheDocumentTags = CacheDocumentTags()
    private val cacheDocuments = CacheDocuments()

    private val profileRequestService = ProfileRequestServicesImpl()
    private val messengerRequestService = MessengersRequestServicesImpl()
    private val tagRequestService = TagRequestServicesImpl()
    private val documentRequestService = DocumentRequestServicesImpl()

    private var savedEmail: String? = null
    private var savedPassword: String? = null

    @Composable
    fun Content() {
        val context = LocalContext.current

        val preferencesManager = PreferencesManager(context)
        savedEmail = preferencesManager.getData(emailKeyPreferences)
        savedPassword = preferencesManager.getData(passwordKeyPreferences)

        getBaseInformationFromDatabase()

        ScreenHandler()
    }

    private fun getBaseInformationFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            getUserProfile()
            getUserMessengers()
            getAllUsers()
            getProfileTags()
            getDocumentTags()
            getDocuments()
        }
    }

    private suspend fun getUserProfile() {
        if (savedEmail != null && savedPassword != null) {
            val usersProfile =
                profileRequestService.getProfileUsingEmailAndPassword(savedEmail!!, savedPassword!!)
            if (usersProfile != null)
                cacheUserProfile.loadUser(usersProfile)
        }
    }

    private suspend fun getUserMessengers() {
        if (cacheUserProfile.getUserFromCache() != null) {
            val userMessengers =
                messengerRequestService.getMessengersPrototype(cacheUserProfile.getUserFromCache()!!)
            cacheUserMessengers.loadMessengers(userMessengers)
        }
    }

    private suspend fun getAllUsers() {
        cacheAllUsersProfile.loadAllUsersProfile(profileRequestService.getAllUsersProfile())
    }

    private suspend fun getProfileTags() {
        cacheProfileTags.loadProfileTags(tagRequestService.getProfileTags())
    }

    private suspend fun getDocumentTags() {
        cacheDocumentTags.loadDocumentTags(tagRequestService.getDocumentTags())
    }

    private suspend fun getDocuments() {
        cacheDocuments.loadDocuments(documentRequestService.getAllDocuments())
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun ScreenHandler() {
        Navigator(DocumentScreen()) { navigator ->
            Scaffold(
                content = { CurrentScreen() },
                bottomBar = { BottomBar(navigator) }
            )
        }
    }

    @Composable
    private fun BottomBar(navigator: Navigator) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                val navigationBar = NavigationBar()
                navigationBar.Content { route ->
                    callbackHandler(navigator)
                    navigationBarHandler(route, navigator)
                }
            }
        }
    }

    private fun callbackHandler(navigator: Navigator) {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigator.pop()
            }
        }
    }

    private fun navigationBarHandler(route: String, navigator: Navigator) {
        if (route == "profile" && cacheUserProfile.getUserFromCache() == null)
            navigator.push(LoginScreen())
        else
            when (route) {
                "documents" -> navigator.push(DocumentScreen())
                "messenger" -> navigator.push(MessengerScreen())
                "add user" -> navigator.push(AddUserScreen())
                "profile" -> navigator.push(ProfileScreen())
            }
    }
}