package com.example.documentsearch.navbar

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.example.documentsearch.R
import com.example.documentsearch.api.SocketManager
import com.example.documentsearch.api.apiRequests.document.DocumentRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.MessengersRequestServicesImpl
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.api.apiRequests.tag.TagRequestServicesImpl
import com.example.documentsearch.api.apiRequests.testConnection.TestConnectionRequestServicesImpl
import com.example.documentsearch.patterns.InternetConnectionFactory
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.document.addDocument.AddDocumentForm
import com.example.documentsearch.screens.errors.FailedConnectionRequest
import com.example.documentsearch.screens.errors.FailedInternetConnection
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.screens.profile.authenticationUser.LoginScreen
import com.example.documentsearch.ui.theme.BackgroundColor
import com.example.documentsearch.ui.theme.cacheAllUserProfile
import com.example.documentsearch.ui.theme.cacheDocumentTags
import com.example.documentsearch.ui.theme.cacheDocuments
import com.example.documentsearch.ui.theme.cacheMessengers
import com.example.documentsearch.ui.theme.cacheProfileTags
import com.example.documentsearch.ui.theme.cacheUserDocuments
import com.example.documentsearch.ui.theme.cacheUserProfile
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

enum class StatusAddDocumentForm { OPEN, CLOSE, NEUTRAL }

class Navigator {
    private val profileRequestService = ProfileRequestServicesImpl()
    private val messengerRequestService = MessengersRequestServicesImpl()
    private val tagRequestService = TagRequestServicesImpl()
    private val documentRequestService = DocumentRequestServicesImpl()
    private val testConnectionRequestService = TestConnectionRequestServicesImpl()

    private val internetConnectionFactory = InternetConnectionFactory()

    private var savedEmail: String? = null
    private var savedPassword: String? = null

    @Composable
    fun Content() {
        val context = LocalContext.current

        val preferencesManager = PreferencesManager(context)
        savedEmail = preferencesManager.getData(emailKeyPreferences)
        savedPassword = preferencesManager.getData(passwordKeyPreferences)

        var isFinishAnimation by remember { mutableStateOf(false) }
        var isInternetConnection by remember {
            mutableStateOf(internetConnectionFactory.internetConnectionCheck(context))
        }
        var isConnectionRequest by remember { mutableStateOf(false) }
        var isCompletedDataDownload by remember { mutableStateOf(false) }

        if (isInternetConnection) {
            LaunchedEffect(Unit) {
                isConnectionRequest =
                    async { testConnectionRequestService.getConnection() }.await()
                if (isConnectionRequest) {
                    getBaseInformationFromDatabase()
                    isCompletedDataDownload = true
                }
            }
        }

        if (isFinishAnimation) {
            if (isInternetConnection) {
                if (isConnectionRequest)
                    SuccessfulInternetConnection()
                else
                    FailRequests(
                        onCompleteChange = { isCompletedDataDownload = true },
                        onRequestChange = { isConnectionRequest = true }
                    )
            } else {
                FailInternetConnection(
                    onCompleteChange = { isCompletedDataDownload = true },
                    onConnectionChange = { isInternetConnection = true }
                )
            }
        }
        else
            LoadingAnimation { isFinishAnimation = true }
    }

    @Composable
    private fun SuccessfulInternetConnection() {
        ScreenHandler()
    }

    @Composable
    fun LoadingAnimation(isFinishAnimation: () -> Unit) {
        val scale = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = { OvershootInterpolator(4f).getInterpolation(it) })
            )

            isFinishAnimation()
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundColor)
        ) {
            Row(modifier = Modifier.scale(scale.value)) {
                Image(
                    painter = painterResource(id = R.drawable.logo_app),
                    modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                    contentDescription = "Logo"
                )
            }
        }
    }

    @Composable
    private fun FailInternetConnection(
        onCompleteChange: () -> Unit,
        onConnectionChange: () -> Unit
    ) {
        val context = LocalContext.current
        var isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                if (internetConnectionFactory.internetConnectionCheck(context)) {
                    getBaseInformationFromDatabase()
                    onCompleteChange()
                    onConnectionChange()
                }
                delay(500)
                isRefreshing = false
            }
        }

        SwipeRefresh(indicatorPadding = PaddingValues(top = 30.dp), state = swipeRefreshState, onRefresh = { isRefreshing = true }) {
            val errorInternetConnection = FailedInternetConnection()
            errorInternetConnection.Content()
        }
    }

    @Composable
    private fun FailRequests(
        onCompleteChange: () -> Unit,
        onRequestChange: () -> Unit
    ) {
        var isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                val isConnection = testConnectionRequestService.getConnection()
                if (isConnection) {
                    getBaseInformationFromDatabase()
                    onCompleteChange()
                    onRequestChange()
                }

                delay(500)
                isRefreshing = false
            }
        }

        SwipeRefresh(indicatorPadding = PaddingValues(top = 30.dp), state = swipeRefreshState, onRefresh = { isRefreshing = true }) {
            val errorConnectionRequest = FailedConnectionRequest()
            errorConnectionRequest.Content()
        }
    }

    private suspend fun getBaseInformationFromDatabase() {
        val time = measureTimeMillis {
            coroutineScope {
                launch {
                    val userProfileDeferred = async { getUserProfile() }
                    val allUsersDeferred = async { getAllUsers() }

                    userProfileDeferred.await()
                    allUsersDeferred.await()

                    launch { getUserDocuments() }
                    launch { getUserMessengers() }
                }
                launch { getProfileTags() }
                launch { getDocumentTags() }
                launch { getDocuments() }
            }
        }
        Log.i("Время запросов", "${(time / 1000)} секунд")
    }

    private suspend fun getUserProfile() {
        if (savedEmail != null && savedPassword != null) {
            val usersProfile =
                profileRequestService.getProfileUsingEmailAndPassword(savedEmail!!, savedPassword!!)
            if (usersProfile != null) {
                cacheUserProfile.value = usersProfile
            }
        }
    }

    private suspend fun getUserDocuments() {
        val userProfile = cacheUserProfile.value
        if (userProfile != null) {
            val documents = documentRequestService.getDocumentsByUserId(userProfile.id!!)
            cacheUserDocuments.value = documents
        }
    }

    private suspend fun getUserMessengers() {
        val userProfile = cacheUserProfile.value
        if (userProfile != null) {
            val userMessengers =
                messengerRequestService.getAllMessengersUsingUserId(userProfile.id!!)
            cacheMessengers.value = userMessengers
            for (messenger in userMessengers) {
                SocketManager.connectCommunicationRoom(messenger.id!!)
                Log.i("Добавление в комнату", "Пользователь добален в комнату ${messenger.id}")
            }
        }
    }

    private suspend fun getAllUsers() {
        val allUsersProfile = profileRequestService.getAllUsersProfile()
        cacheAllUserProfile.value = allUsersProfile
    }

    private suspend fun getProfileTags() {
        val profileTags = tagRequestService.getProfileTags()
        cacheProfileTags.value = profileTags
    }

    private suspend fun getDocumentTags() {
        val documentTags = tagRequestService.getDocumentTags()
        cacheDocumentTags.value = documentTags
    }

    private suspend fun getDocuments() {
        cacheDocuments.value = documentRequestService.getAllDocuments()
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
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .zIndex(10f)
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
        if (route == "profile" && cacheUserProfile.value == null)
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