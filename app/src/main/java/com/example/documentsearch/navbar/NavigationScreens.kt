package com.example.documentsearch.navbar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.documentsearch.dataClasses.DocumentWithPercentage
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.header.profile.HeaderProfileDataChanged
import com.example.documentsearch.header.profile.HeaderProfilePasswordChanged
import com.example.documentsearch.screens.addUser.AddUserScreen
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

/**
 * Функция предназначена для перемещения пользователей по разным экранам
 * @param navController Контроллер для навигации
 * @param profile Объект профиля пользователя
 * @param listDocuments Список всех документов
 * @param tagsDocumentation Список тегов
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    profile: Profile,
    listDocuments: SnapshotStateList<DocumentWithPercentage>,
    tagsDocumentation: List<String>,
    tagsProfile: List<String>
) {
    // Контенер для экранов
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Блок в котором хранятся экраны навигации
                NavHost(navController,
                    startDestination = NavigationItem.Documents.route,
                    enterTransition = { fadeIn(animationSpec = tween(0)) },
                    exitTransition = { fadeOut(animationSpec = tween(0)) }) {
                    // Навигация на экран с документацией
                    composable(NavigationItem.Documents.route) {

                        activeItem.value = NavigationItem.Documents.route
                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            com.example.documentsearch.header.documentScreen.Header(
                                tagsDocumentation
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(5.dp, 120.dp, 5.dp, 0.dp)
                                .zIndex(0f)
                                .fillMaxHeight()
                                .align(Alignment.Center)
                        ) {
                            com.example.documentsearch.screens.document.DocumentScreen(listDocumet = listDocuments)
                        }
                    }
                    // Навигация на экран с мессенджером
                    composable(NavigationItem.Messenger.route) {
                        activeItem.value = NavigationItem.Messenger.route
                        MessengerScreen()
                    }
                    // Навигация на экран с добавлением пользователей
                    composable(NavigationItem.AddUser.route) {
                        activeItem.value = NavigationItem.AddUser.route
                        AddUserScreen()
                    }
                    // Навигация на экран с профилем
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
                                navController = navController, profile = profile, tags = tagsProfile
                            )
                        }
                    }
                    // Навигация на экран с входом пользователем
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
                    // Навигация на экран с регистрацией пользователя
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
                    // Навигация на экран с кодом для регистрации
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
                    // Навигация на экран с восстановлением пароля
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
                    // Навигация на экран с восстановлением пароля(через email)
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
                    // Навигация на экран с кодом для восстановления пароля
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
                    // Навигация на экран с заменой пароля при восстановлении пароля
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
                    // Навигация на экран с заменой персонального имени
                    composable(NavigationItem.ChangePersonalName.route) {
                        activeItem.value = NavigationItem.ChangePersonalName.route

                        // Персональное имя при изменении
                        var changedPersonalName by remember { mutableStateOf(profile.personalName) }

                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            HeaderProfileDataChanged(
                                navController = navController,
                                title = "Имя пользователя",
                                value = changedPersonalName,
                                changeValue = { profile.personalName = it },
                                conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(
                                    changedPersonalName
                                )
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
                                conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(
                                    changedPersonalName
                                )
                            )
                        }
                    }
                    // Навигация на экран с заменой информации о пользователе
                    composable(NavigationItem.ChangePersonalInfo.route) {
                        activeItem.value = NavigationItem.ChangePersonalInfo.route

                        var changedPersonalInfo by remember { mutableStateOf(profile.personalInfo) } // Значение информации о пользователе в поле

                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            HeaderProfileDataChanged(
                                navController = navController,
                                title = "О себе",
                                value = changedPersonalInfo,
                                changeValue = { profile.personalInfo = it },
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
                    // Навигация на экран с заменой номера телефона
                    composable(NavigationItem.ChangeNumberPhone.route) {
                        activeItem.value = NavigationItem.ChangeNumberPhone.route

                        var changedNumberPhone by remember { mutableStateOf(profile.numberPhone) } // Значение номера телефона в поле

                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            HeaderProfileDataChanged(
                                navController = navController,
                                title = "Номер телефона",
                                value = changedNumberPhone,
                                changeValue = { profile.numberPhone = it },
                                conditionValidation = Validation().isValidPhone(
                                    changedNumberPhone
                                ) && changedNumberPhone.length == 11
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
                                conditionValidation = Validation().isValidPhone(
                                    changedNumberPhone
                                ) && changedNumberPhone.length == 11
                            )
                        }
                    }
                    // Навигация на экран с заменой email
                    composable(NavigationItem.ChangeEmail.route) {
                        activeItem.value = NavigationItem.ChangeEmail.route

                        var changedEmail by remember { mutableStateOf(profile.email) } // Значение email в поле

                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            HeaderProfileDataChanged(
                                navController = navController,
                                title = "Email",
                                value = changedEmail,
                                changeValue = { profile.email = it },
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
                    // Навигация на экран с заменой пароля
                    composable(NavigationItem.ChangePassword.route) {
                        activeItem.value = NavigationItem.ChangePassword.route

                        var changedOldPassword by remember { mutableStateOf("") } // Значение старого пароля в поле
                        var changedNewPassword by remember { mutableStateOf("") } // Значение нового пароля в поле
                        var changedRepeatPassword by remember { mutableStateOf("") } // Значение повтора пароля
                        Box(
                            modifier = Modifier
                                .zIndex(100f)
                                .align(Alignment.TopCenter)
                        ) {
                            HeaderProfilePasswordChanged(navController = navController,
                                oldPassword = profile.password,
                                oldPasswordEnter = changedOldPassword,
                                newPassword = changedNewPassword,
                                repeatPassword = changedRepeatPassword,
                                changeValue = { profile.password = it })
                        }
                        Box(
                            modifier = Modifier
                                .zIndex(0f)
                                .padding(0.dp, 85.dp, 0.dp, 0.dp)
                                .fillMaxHeight()
                                .align(Alignment.Center)
                        ) {
                            ReplacePasswordScreen(oldPassword = changedOldPassword,
                                oldPasswordChanged = { changedOldPassword = it },
                                newPassword = changedNewPassword,
                                newPasswordChanged = { changedNewPassword = it },
                                repeatPassword = changedRepeatPassword,
                                repeatPasswordChanged = { changedRepeatPassword = it })
                        }
                    }
                }
                // Блок с навигационным меню
                Box(
                    modifier = Modifier
                        .zIndex(100f)
                        .align(Alignment.BottomCenter)
                ) { Navbar(navController) }
            }
        },
    )
}