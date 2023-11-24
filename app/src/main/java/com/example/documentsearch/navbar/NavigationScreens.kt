package com.example.documentsearch.navbar

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.prototypes.AnotherUserPrototype
import com.example.documentsearch.prototypes.DocumentWithPercentage
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.ProfilePrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.header.profile.HeaderProfileDataChanged
import com.example.documentsearch.header.profile.HeaderProfilePasswordChanged
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.addUser.ProfileInfo
import com.example.documentsearch.screens.messenger.BottomBar
import com.example.documentsearch.screens.messenger.Communication
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime


/**
 * Функция предназначена для перемещения пользователей по разным экранам
 * @param navController Контроллер для навигации
 * @param profile Объект профиля пользователя
 * @param listDocuments Список всех документов
 * @param tagsDocumentation Список тегов
 */
@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    profile: ProfilePrototype?,
    anotherUsers: List<AnotherUserPrototype>?,
    onProfileChange: (ProfilePrototype?) -> Unit,
    listDocuments: MutableList<DocumentWithPercentage>,
    tagsDocumentation: List<TagPrototype>,
    tagsProfile: List<TagPrototype>,
    listMessenger: MutableList<MessengerPrototype>?,
    onListMessenger: (MutableList<MessengerPrototype>) -> Unit
) {
    var registrationData by remember { mutableStateOf<ProfilePrototype?>(null) }
    var changedPersonalName by remember { mutableStateOf(profile?.personalName ?: "") }
    var changedPersonalInfo by remember { mutableStateOf(profile?.personalInfo ?: "") } // Значение информации о пользователе в поле
    var changedNumberPhone by remember { mutableStateOf(profile?.telephoneNumber ?: "") } // Значение номера телефона в поле
    var changedEmail by remember { mutableStateOf(profile?.email ?: "") } // Значение email в поле
    var changedOldPassword by remember { mutableStateOf("") } // Значение старого пароля в поле
    var changedNewPassword by remember { mutableStateOf("") } // Значение нового пароля в поле
    var changedRepeatPassword by remember { mutableStateOf("") } // Значение повтора пароля

    var isValidPersonalName by remember { mutableStateOf(true) }
    var isValidEmail by remember { mutableStateOf(true) }
    var isValidNumberPhone by remember { mutableStateOf(true) }

    var forgotCode by remember { mutableIntStateOf(-1) }
    var forgotPhoneNumber by remember { mutableStateOf<String?>(null) }
    var forgotEmail by remember { mutableStateOf<String?>(null) }

    var selectedMessenger by remember { mutableStateOf<MessengerPrototype?>(null) }
    var selectedUser by remember { mutableLongStateOf(-1L) }
    val rememberScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        rememberScrollState.scrollTo(rememberScrollState.maxValue)
    }

    NavHost(
        navController,
        startDestination = NavigationItem.Documents.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(150)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(150)
            )
        },
        modifier = Modifier
            .imePadding()
            .zIndex(1f)
    ) {
        // Навигация на экран с документацией
        composable(NavigationItem.Documents.route) {
            activeItem.value = NavigationItem.Documents.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.documentScreen.Header(tagsDocumentation) }
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp, 120.dp, 5.dp, 0.dp)
                        .zIndex(0f)
                ) {
                    com.example.documentsearch.screens.document.DocumentScreen(listDocumet = listDocuments)
                }
            }
        }


        // Навигация на экран с мессенджером
        composable(NavigationItem.Messenger.route) {
            activeItem.value = NavigationItem.Messenger.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.messengerScreen.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .padding(0.dp, 88.dp, 0.dp, 0.dp)
                        .zIndex(0f)
                ) {
                    MessengerScreen(
                        navController = navController,
                        listMessenger = listMessenger,
                        onMessengerChange = { selectedMessenger = it },
                        profile = profile
                    )
                }
            }

        }
        // Навигация на экран переписки с пользователем
        composable(NavigationItem.Communication.route) {
            activeItem.value = NavigationItem.Communication.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.messengerScreen.Header() },
                bottomBar = {
                    Box(modifier = Modifier.imePadding()) {
                        BottomBar(
                            onMessageChange = { message ->
                                listMessenger?.first { it == selectedMessenger }?.listMessage!!.add(
                                    MessagePrototype(
                                        LocalDate.now().toString(),
                                        "${LocalTime.now().hour}.${LocalTime.now().minute}",
                                        message,
                                        selectedMessenger!!.id!!,
                                        true
                                    )
                                )
                                onListMessenger(listMessenger)
                            },
                            rememberScrollState = rememberScrollState
                        )
                    }
                }) {
                Box(
                    modifier = Modifier
                        .padding(0.dp, 88.dp, 0.dp, 0.dp)
                        .zIndex(0f)
                ) {
                    Communication(
                        navController = navController,
                        messenger = listMessenger!!.first { it == selectedMessenger },
                        rememberScrollState = rememberScrollState
                    )
                }
            }
        }


        // Навигация на экран с добавлением пользователей
        composable(NavigationItem.AddUser.route) {
            activeItem.value = NavigationItem.AddUser.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.addFriend.Header(tagsDocumentation) }
            ) {
                Box(
                    modifier = Modifier
                        .padding(0.dp, 125.dp, 0.dp, 0.dp)
                        .zIndex(0f)
                ) {
                    AddUserScreen(
                        navController = navController,
                        users = anotherUsers,
                        onUserChange = { selectedUser = it },
                        profile = profile)
                }
            }

        }
        // Навигация на экран с информацией о пользователе
        composable(NavigationItem.ProfileInfo.route) {
            activeItem.value = NavigationItem.ProfileInfo.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.addFriend.HeaderProfileUser() }
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp, 92.dp, 5.dp, 0.dp)
                        .zIndex(0f)
                ) {
                    ProfileInfo(
                        navController = navController,
                        anotherProfile = anotherUsers!!.first { it.id == selectedUser },
                        profile = profile!!
                    ) {
                        selectedMessenger = it
                        if (listMessenger != null) {
                            listMessenger.add(it)
                            onListMessenger(listMessenger)
                        }
                        else
                            onListMessenger(mutableListOf(it))
                        navController.navigate(NavigationItem.Communication.route)
                    }
                }
            }
        }

        // Навигация на экран с профилем
        composable(NavigationItem.Profile.route) {
            activeItem.value = NavigationItem.Profile.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    if (profile == null)
                        navController.navigate(NavigationItem.Login.route)
                    else
                        ProfileScreen(
                            navController = navController,
                            profile = profile,
                            tags = tagsProfile,
                            onExitProfileChange = {
                                if (it)
                                    onProfileChange(null)
                            }
                        )

                }
            }

        }
        // Навигация на экран с входом пользователем
        composable(NavigationItem.Login.route) {
            activeItem.value = NavigationItem.Login.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    Login(navController) { onProfileChange(it) }
                }
            }

        }
        // Навигация на экран с регистрацией пользователя
        composable(NavigationItem.Registration.route) {
            activeItem.value = NavigationItem.Registration.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    Registration(navController) { registrationData = it }
                }
            }

        }
        // Навигация на экран с кодом для регистрации
        composable(NavigationItem.VerificationRegistration.route) {
            activeItem.value = NavigationItem.VerificationRegistration.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    VerificationRegistration(navController, registrationData!!) {
                        onProfileChange(it)
                    }
                }
            }

        }
        // Навигация на экран с восстановлением пароля
        composable(NavigationItem.ForgotPassword.route) {
            activeItem.value = NavigationItem.ForgotPassword.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    ForgotPassword(
                        navController = navController,
                        onPhoneNumberChange = { forgotPhoneNumber = it },
                        onForgotCodeChange = { forgotCode = it })
                }
            }

        }
        // Навигация на экран с восстановлением пароля(через email)
        composable(NavigationItem.AnotherForgotPassword.route) {
            activeItem.value = NavigationItem.AnotherForgotPassword.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    AnotherForgotPassword(
                        navController = navController,
                        onEmailChange = { forgotEmail = it },
                        onForgotCodeChange = { forgotCode = it })
                }
            }

        }
        // Навигация на экран с кодом для восстановления пароля
        composable(NavigationItem.ForgotCode.route) {
            activeItem.value = NavigationItem.ForgotCode.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    ForgotCode(navController, forgotCode)
                }
            }

        }
        // Навигация на экран с заменой пароля при восстановлении пароля
        composable(NavigationItem.NewPassword.route) {
            activeItem.value = NavigationItem.NewPassword.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = { com.example.documentsearch.header.profile.Header() }
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(5.dp, 85.dp, 5.dp, 0.dp)
                ) {
                    NewPassword(navController, forgotEmail, forgotPhoneNumber)
                }
            }

        }


        // Навигация на экран с заменой персонального имени
        composable(NavigationItem.ChangePersonalName.route) {
            activeItem.value = NavigationItem.ChangePersonalName.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    HeaderProfileDataChanged(
                        navController = navController,
                        title = "Имя пользователя",
                        value = changedPersonalName,
                        changeValue = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val personalName: String? =
                                    ProfileRequestServicesImpl().updatePersonalNameUsingEmail(profile!!.email, it)
                                if (personalName != null) {
                                    profile.personalName = it
                                    navController.navigate(NavigationItem.Profile.route)
                                }
                            }
                        },
                        conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(
                            changedPersonalName
                        ) && isValidPersonalName
                    )
                }) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                ) {
                    StandardChangingInfoScreen(
                        value = changedPersonalName,
                        valueChanged = {
                            changedPersonalName = it
                            CoroutineScope(Dispatchers.Main).launch {
                                val profileByPersonalName =
                                    ProfileRequestServicesImpl().getProfileUsingPersonalName(it)
                                isValidPersonalName =
                                    !(profileByPersonalName != null && it.isNotEmpty() && profileByPersonalName != profile)
                            }
                        },
                        helpText = "Вы можете задать публичное пользовательское имя. При помощи этого имени вас легко смогут найти пользователи и связаться с вами.\n\nПользовательское имя должно быть не менее 5 символов. Пользовательское имя может содержать символы a-z, 0-9 и подчёркивания.",
                        label = "Задать пользовательское имя",
                        placeholder = "Имя пользователя",
                        conditionValidation = Regex("^[a-zA-z0-9_]+$").matches(
                            changedPersonalName
                        ) && isValidPersonalName
                    )
                }
            }

        }
        // Навигация на экран с заменой информации о пользователе
        composable(NavigationItem.ChangePersonalInfo.route) {
            activeItem.value = NavigationItem.ChangePersonalInfo.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    HeaderProfileDataChanged(
                        navController = navController,
                        title = "О себе",
                        value = changedPersonalInfo,
                        changeValue = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val personalInfo: String? =
                                    ProfileRequestServicesImpl().updatePersonalInfoUsingEmail(profile!!.email, it)
                                if (personalInfo != null) {
                                    profile.personalInfo = it
                                    navController.navigate(NavigationItem.Profile.route)
                                }
                            }
                        },
                        conditionValidation = true
                    )
                }) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
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

        }
        // Навигация на экран с заменой номера телефона
        composable(NavigationItem.ChangeNumberPhone.route) {
            activeItem.value = NavigationItem.ChangeNumberPhone.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    HeaderProfileDataChanged(
                        navController = navController,
                        title = "Номер телефона",
                        value = changedNumberPhone,
                        changeValue = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val phoneNumber: String? =
                                    ProfileRequestServicesImpl().updateNumberPhoneUsingEmail(profile!!.email, it)
                                if (phoneNumber != null) {
                                    profile.telephoneNumber = it
                                    navController.navigate(NavigationItem.Profile.route)
                                }
                            }
                        },
                        conditionValidation = Validation().isValidPhone(
                            changedNumberPhone
                        ) && changedNumberPhone.length == 11 && isValidNumberPhone
                    )
                }) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                ) {
                    PhoneNumberChangingScreen(
                        value = changedNumberPhone,
                        valueChanged = {
                            changedNumberPhone = it
                            CoroutineScope(Dispatchers.Main).launch {
                                val profileByPhoneNumber =
                                    ProfileRequestServicesImpl().getProfileUsingPhoneNumber(it)
                                isValidNumberPhone =
                                    !(profileByPhoneNumber != null && it.isNotEmpty() && profileByPhoneNumber != profile)
                            }
                        },
                        validationText = "Здесь вы можете ввести свой новый номер телефона. \nНомер телефона должен использовать международный формат.",
                        label = "Напишите свой номер",
                        conditionValidation = Validation().isValidPhone(
                            changedNumberPhone
                        ) && changedNumberPhone.length == 11 && isValidNumberPhone
                    )
                }
            }

        }
        // Навигация на экран с заменой email
        composable(NavigationItem.ChangeEmail.route) {
            activeItem.value = NavigationItem.ChangeEmail.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    HeaderProfileDataChanged(
                        navController = navController,
                        title = "Email",
                        value = changedEmail,
                        changeValue = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val email: String? =
                                    ProfileRequestServicesImpl().updateEmailUsingOldEmail(profile!!.email, it)
                                if (email != null) {
                                    profile.email = it
                                    navController.navigate(NavigationItem.Profile.route)
                                }
                            }
                        },
                        conditionValidation = Validation().isValidEmail(changedEmail) && isValidEmail
                    )
                }) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
                ) {
                    StandardChangingInfoScreen(
                        value = changedEmail,
                        valueChanged = {
                            changedEmail = it
                            CoroutineScope(Dispatchers.Main).launch {
                                val profileByEmail = ProfileRequestServicesImpl().getProfileUsingEmail(it)
                                isValidEmail =
                                    !(profileByEmail != null && it.isNotEmpty() && profileByEmail != profile)
                            }
                        },
                        helpText = "Здесь вы можете сменить свой адрес электронной почты.",
                        label = "Напишите свой email",
                        placeholder = "ivan.ivanov@gmail.com",
                        conditionValidation = Validation().isValidEmail(changedEmail) && isValidEmail
                    )
                }
            }

        }
        // Навигация на экран с заменой пароля
        composable(NavigationItem.ChangePassword.route) {
            activeItem.value = NavigationItem.ChangePassword.selectionNavbar

            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                topBar = {
                    HeaderProfilePasswordChanged(
                        navController = navController,
                        oldPassword = profile!!.password,
                        oldPasswordEnter = changedOldPassword,
                        newPassword = changedNewPassword,
                        repeatPassword = changedRepeatPassword,
                        changeValue = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val password: String? =
                                    ProfileRequestServicesImpl().updatePasswordUsingEmail(
                                        profile.email,
                                        changedOldPassword,
                                        it
                                    )
                                if (password != null) {
                                    println("Пароль успешно обновлён")
                                }
                            }
                        }
                    )
                }) {
                Box(
                    modifier = Modifier
                        .zIndex(0f)
                        .padding(0.dp, 85.dp, 0.dp, 0.dp)
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
    }

    if (activeItem.value != NavigationItem.Communication.selectionNavbar) {
        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Navbar(navController = navController)
            }
        }
    }
}