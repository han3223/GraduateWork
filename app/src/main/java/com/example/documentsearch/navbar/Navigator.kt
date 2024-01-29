package com.example.documentsearch.navbar

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.example.documentsearch.api.apiRequests.document.DocumentRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.cache.CacheAllUsersProfile
import com.example.documentsearch.cache.CacheDocumentTags
import com.example.documentsearch.cache.CacheDocuments
import com.example.documentsearch.cache.CacheProfileTags
import com.example.documentsearch.cache.CacheUserMessengers
import com.example.documentsearch.cache.CacheUserProfile
import com.example.documentsearch.patterns.InternetConnectionFactory
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.document.addDocument.AddDocumentForm
import com.example.documentsearch.screens.errors.FailedInternetConnection
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.screens.profile.authenticationUser.LoginScreen
import com.example.documentsearch.ui.theme.BackgroundColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

enum class StatusAddDocumentForm { OPEN, CLOSE, NEUTRAL }

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

    private val internetConnectionFactory = InternetConnectionFactory()

    private var savedEmail: String? = null
    private var savedPassword: String? = null

    @Composable
    fun Content() {
        val context = LocalContext.current

        val preferencesManager = PreferencesManager(context)
        savedEmail = preferencesManager.getData(emailKeyPreferences)
        savedPassword = preferencesManager.getData(passwordKeyPreferences)

        var isInternetConnection by remember {
            mutableStateOf(
                internetConnectionFactory.internetConnectionCheck(
                    context
                )
            )
        }
        var isCompletedDataDownload by remember { mutableStateOf(false) }

        if (isInternetConnection) {
            SuccessfulInternetConnection(isCompletedDataDownload) { isCompletedDataDownload = it }
        } else {
            FailInternetConnection(
                onCompleteChange = { isCompletedDataDownload = it },
                onConnectionChange = { isInternetConnection = it }
            )
        }
    }

    @Composable
    private fun SuccessfulInternetConnection(
        isCompletedDataDownload: Boolean,
        onCompleteChange: (Boolean) -> Unit
    ) {
        if (isCompletedDataDownload)
            ScreenHandler()
        else {
            LaunchedEffect(Unit) {
                getBaseInformationFromDatabase()
                getUserMessengers()
                onCompleteChange(true)
            }
        }
    }

    @Composable
    private fun FailInternetConnection(
        onCompleteChange: (Boolean) -> Unit,
        onConnectionChange: (Boolean) -> Unit
    ) {
        val context = LocalContext.current
        var isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                if (internetConnectionFactory.internetConnectionCheck(context)) {
                    getBaseInformationFromDatabase()
                    getUserMessengers()
                    onCompleteChange(true)
                    onConnectionChange(true)
                }
                delay(1000)
                isRefreshing = false
            }
        }

        SwipeRefresh(state = swipeRefreshState, onRefresh = { isRefreshing = true }) {
            val errorInternetConnection = FailedInternetConnection()
            errorInternetConnection.Content()
        }
    }

    private suspend fun getBaseInformationFromDatabase() {
        getUserProfile()
        getAllUsers()
        getProfileTags()
        getDocumentTags()
        getDocuments()
    }

    private suspend fun getUserProfile() {
        if (savedEmail != null && savedPassword != null) {
            val usersProfile =
                profileRequestService.getProfileUsingEmailAndPassword(savedEmail!!, savedPassword!!)
            if (usersProfile != null) {
                cacheUserProfile.loadUser(usersProfile)
            }
        }
    }

    private suspend fun getUserMessengers() {
        val userProfile = cacheUserProfile.getUserFromCache()
        if (userProfile != null) {
            val userMessengers =
                messengerRequestService.getMessengersPrototype(userProfile)
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
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BackgroundColor)
                    ) {
                        FadeTransition(navigator)
                    }
                },
                bottomBar = { BottomBar(navigator) }
            )
        }
    }

    @Composable
    private fun BottomBar(navigator: Navigator) {
        var statusAddDocumentForm by remember { mutableStateOf(StatusAddDocumentForm.CLOSE.name) }

        if (navigator.lastItem.key != "com.example.documentsearch.screens.messenger.communication.CommunicationScreen") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .zIndex(10f),
                contentAlignment = Alignment.BottomCenter
            ) {
                AddDocumentForm().Content(statusAddDocumentForm) { statusAddDocumentForm = it }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    val navigationBar = NavigationBar()
                    navigationBar.Content(
                        onSelectedScreen = { route ->
                            callbackHandler(navigator)
                            navigationBarHandler(route, navigator)
                        },
                        onClickAddDocumentChange = {
                            statusAddDocumentForm = StatusAddDocumentForm.OPEN.name
                        }
                    )
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