@file:OptIn(ExperimentalAnimationApi::class)

package com.example.documentsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentsearch.header.documentScreen.icon
import com.example.documentsearch.header.profile.HeaderProfileDataChanged
import com.example.documentsearch.header.profile.HeaderProfilePasswordChanged
import com.example.documentsearch.navbar.Navbar
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.navbar.activeItem
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.screens.profile.authenticationUser.AnotherForgotPassword
import com.example.documentsearch.screens.profile.authenticationUser.ForgotCode
import com.example.documentsearch.screens.profile.authenticationUser.ForgotPassword
import com.example.documentsearch.screens.profile.authenticationUser.Login
import com.example.documentsearch.screens.profile.authenticationUser.NewPassword
import com.example.documentsearch.screens.profile.authenticationUser.Registration
import com.example.documentsearch.screens.profile.authenticationUser.VerificationRegistration
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.PhoneNumberChangingScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ReplacePasswordScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.StandardChangingInfoScreen
import com.example.documentsearch.validation.Validation
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }

            val navController = rememberNavController()

            // Получите onBackPressedDispatcher и создайте onBackPressedCallback
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Вызовите навигацию назад
                    navController.navigateUp()
                }
            }
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

            var personalName by remember { mutableStateOf("IVazhen") }
            var personalInfo by remember { mutableStateOf("Я занимаю разработкой программного обеспечения для андроид устройств, а также увлекаюсь квантовой физикой.") }
            var numberPhone by remember { mutableStateOf("79116172604") }
            var email by remember { mutableStateOf("ivan.vazhenin34@gmail.com") }
            var password by remember { mutableStateOf("qwerty123") }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                if (icon.intValue != R.drawable.active_filter)
                                    icon.intValue = R.drawable.header
                            },
                        )
                    },
                content = { padding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            navController,
                            startDestination = NavigationItem.Documents.route,
                            enterTransition = { fadeIn(animationSpec = tween(0)) },
                            exitTransition = { fadeOut(animationSpec = tween(0)) }
                        ) {
                            composable(NavigationItem.Documents.route) {
                                activeItem.value = NavigationItem.Documents.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.documentScreen.Header() }
                                Box(
                                    modifier = Modifier
                                        .padding(5.dp, 120.dp, 5.dp, 0.dp)
                                        .zIndex(0f)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    DocumentScreen()
                                }
                            }

                            composable(NavigationItem.Messenger.route) {
                                activeItem.value = NavigationItem.Messenger.route
                                MessengerScreen()
                            }

                            composable(NavigationItem.AddUser.route) {
                                activeItem.value = NavigationItem.AddUser.route
                                AddUserScreen()
                            }

                            composable(NavigationItem.Profile.route) {
                                activeItem.value = NavigationItem.Profile.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    ProfileScreen(
                                        navController = navController,
                                        personalName = personalName,
                                        personalInfo = personalInfo,
                                        numberPhone = numberPhone,
                                        email = email,
                                    )
                                }
                            }

                            composable(NavigationItem.Login.route) {
                                activeItem.value = NavigationItem.Login.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    Login(navController)
                                }
                            }

                            composable(NavigationItem.Registration.route) {
                                activeItem.value = NavigationItem.Registration.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    Registration(navController)
                                }
                            }

                            composable(NavigationItem.VerificationRegistration.route) {
                                activeItem.value = NavigationItem.VerificationRegistration.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    VerificationRegistration(navController)
                                }
                            }

                            composable(NavigationItem.ForgotPassword.route) {
                                activeItem.value = NavigationItem.ForgotPassword.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    ForgotPassword(navController)
                                }
                            }

                            composable(NavigationItem.AnotherForgotPassword.route) {
                                activeItem.value = NavigationItem.AnotherForgotPassword.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    AnotherForgotPassword(navController)
                                }
                            }

                            composable(NavigationItem.ForgotCode.route) {
                                activeItem.value = NavigationItem.ForgotCode.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    ForgotCode(navController)
                                }
                            }

                            composable(NavigationItem.NewPassword.route) {
                                activeItem.value = NavigationItem.NewPassword.route
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) { com.example.documentsearch.header.profile.Header() }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    NewPassword(navController)
                                }
                            }

                            composable(NavigationItem.ChangePersonalName.route) {
                                activeItem.value = NavigationItem.ChangePersonalName.route

                                var changedPersonalName by remember { mutableStateOf(personalName) }

                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) {
                                    HeaderProfileDataChanged(
                                        navController = navController,
                                        label = "Имя пользователя",
                                        value = changedPersonalName,
                                        changeValue = { personalName = it },
                                        conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(changedPersonalName)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    StandardChangingInfoScreen(
                                        value = changedPersonalName,
                                        valueChanged = { changedPersonalName = it },
                                        helpText = "Вы можете задать публичное пользовательское имя. При помощи этого имени вас легко смогут найти пользователи и связаться с вами.\n\nПользовательское имя должно быть не менее 5 символов. Пользовательское имя может содержать символы a-z, 0-9 и подчёркивания.",
                                        label = "Задать пользовательское имя",
                                        placeholder = "Имя пользователя",
                                        conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(changedPersonalName)
                                    )
                                }
                            }

                            composable(NavigationItem.ChangePersonalInfo.route) {
                                activeItem.value = NavigationItem.ChangePersonalInfo.route

                                var changedPersonalInfo by remember { mutableStateOf(personalInfo) }
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) {
                                    HeaderProfileDataChanged(
                                        navController = navController,
                                        label = "О себе",
                                        value = changedPersonalInfo,
                                        changeValue = { personalInfo = it },
                                        conditionValidation = true
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    StandardChangingInfoScreen(
                                        value = changedPersonalInfo,
                                        valueChanged = { changedPersonalInfo = it },
                                        helpText = "Здесь вы можете кратко написать о себе. Эта информация будет видна пользователям, которые зайдут в ваш профиль.",
                                        label = "Расскажите о себе",
                                        placeholder = "О себе",
                                        singleLine = false,
                                        counter = true,
                                        count = 115
                                    )
                                }
                            }

                            composable(NavigationItem.ChangeNumberPhone.route) {
                                activeItem.value = NavigationItem.ChangeNumberPhone.route

                                var changedNumberPhone by remember { mutableStateOf(numberPhone) }
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) {
                                    HeaderProfileDataChanged(
                                        navController = navController,
                                        label = "Номер телефона",
                                        value = changedNumberPhone,
                                        changeValue = { numberPhone = it },
                                        conditionValidation = Validation().isValidPhone(changedNumberPhone) && changedNumberPhone.length == 11
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    PhoneNumberChangingScreen(
                                        value = changedNumberPhone,
                                        valueChanged = { changedNumberPhone = it },
                                        validationText = "Здесь вы можете ввести свой новый номер телефона. \nНомер телефона должен использовать международный формат.",
                                        label = "Напишите свой номер",
                                        conditionValidation = Validation().isValidPhone(changedNumberPhone) && changedNumberPhone.length == 11
                                    )
                                }
                            }

                            composable(NavigationItem.ChangeEmail.route) {
                                activeItem.value = NavigationItem.ChangeEmail.route

                                var changedEmail by remember { mutableStateOf(email) }
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) {
                                    HeaderProfileDataChanged(
                                        navController = navController,
                                        label = "Email",
                                        value = changedEmail,
                                        changeValue = { email = it },
                                        conditionValidation = Validation().isValidEmail(changedEmail)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    StandardChangingInfoScreen(
                                        value = changedEmail,
                                        valueChanged = { changedEmail = it },
                                        helpText = "Здесь вы можете сменить свой адрес электронной почты.",
                                        label = "Напишите свой email",
                                        placeholder = "ivan.ivanov@gmail.com",
                                        conditionValidation = Validation().isValidEmail(changedEmail)
                                    )
                                }
                            }

                            composable(NavigationItem.ChangePassword.route) {
                                activeItem.value = NavigationItem.ChangePassword.route

                                var changedOldPassword by remember { mutableStateOf("") }
                                var changedNewPassword by remember { mutableStateOf("") }
                                var changedRepeatPassword by remember { mutableStateOf("") }
                                Box(
                                    modifier = Modifier
                                        .zIndex(100f)
                                        .align(Alignment.TopCenter)
                                ) {
                                    HeaderProfilePasswordChanged(
                                        navController = navController,
                                        oldPassword = password,
                                        oldPasswordEnter = changedOldPassword,
                                        newPassword = changedNewPassword,
                                        repeatPassword = changedRepeatPassword,
                                        changeValue = { password = it }
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .zIndex(0f)
                                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                        .fillMaxHeight()
                                        .align(Alignment.Center)
                                ) {
                                    ReplacePasswordScreen(
                                        oldPassword = changedOldPassword,
                                        oldPasswordChanged = { changedOldPassword = it },
                                        newPassword = changedNewPassword,
                                        newPasswordChanged = { changedNewPassword = it },
                                        repeatPassword = changedRepeatPassword,
                                        repeatPasswordChanged = { changedRepeatPassword = it }
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.BottomCenter)
                        ) { Navbar(navController) }
                    }
                },
            )
        }
    }
}
