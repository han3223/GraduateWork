package com.example.documentsearch.navbar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.DocumentWithPercentage
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.addUser.AddUserScreen
import com.example.documentsearch.screens.addUser.ProfileInfo
import com.example.documentsearch.screens.document.DocumentScreen
import com.example.documentsearch.screens.messenger.MessengerScreen
import com.example.documentsearch.screens.messenger.communication.CommunicationScreen
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.screens.profile.authenticationUser.AnotherForgotPasswordScreen
import com.example.documentsearch.screens.profile.authenticationUser.ForgotCodeScreen
import com.example.documentsearch.screens.profile.authenticationUser.ForgotPasswordScreen
import com.example.documentsearch.screens.profile.authenticationUser.LoginScreen
import com.example.documentsearch.screens.profile.authenticationUser.NewPasswordScreen
import com.example.documentsearch.screens.profile.authenticationUser.RegistrationScreen
import com.example.documentsearch.screens.profile.authenticationUser.VerificationRegistrationScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.BasicChangingInfoScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.PhoneNumberChangingScreen
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.ReplacePasswordScreen
import com.example.documentsearch.validation.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime



class Navigation(
    navigationController: NavHostController,
    userProfile: UserProfilePrototype?,
    userMessengers: List<MessengerPrototype>,
    allUsersProfiles: List<AnotherUserProfilePrototype>,
    profileTags: List<TagPrototype>,
    documentTags: List<TagPrototype>,
    documents: List<DocumentWithPercentage>
) : NavigationBar() {
    private val navigationController: NavHostController
    private val userProfile: UserProfilePrototype?
    private val userMessengers: MutableList<MessengerPrototype>
    private val allUsers: List<AnotherUserProfilePrototype>
    private val profileTags: List<TagPrototype>
    private val documentTags: List<TagPrototype>
    private val documents: List<DocumentWithPercentage>

    init {
        this.navigationController = navigationController
        this.userProfile = userProfile
        this.userMessengers = userMessengers.toMutableList()
        this.allUsers = allUsersProfiles
        this.profileTags = profileTags
        this.documentTags = documentTags
        this.documents = documents
    }

    @SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun NavigationScreens(
        onProfileChange: (UserProfilePrototype?) -> Unit,
        onListMessenger: (List<MessengerPrototype>) -> Unit
    ) {
        var registrationData by remember { mutableStateOf<UserProfilePrototype?>(null) }

        var forgotCode by remember { mutableIntStateOf(-1) }
        var forgotNumberPhone by remember { mutableStateOf<String?>(null) }
        var forgotEmail by remember { mutableStateOf<String?>(null) }

        var selectedMessenger by remember { mutableStateOf<MessengerPrototype?>(null) }
        var selectedUser by remember { mutableLongStateOf(-1L) }
        val rememberScrollState = rememberScrollState()

        LaunchedEffect(Unit) { rememberScrollState.scrollTo(rememberScrollState.maxValue) }

//        Navigator(DocumentScreen(documentTags, documents))

        NavHost(
            navigationController,
            startDestination = NavigationItem.Documents.route,
            modifier = Modifier.imePadding().zIndex(1f)
        ) {
            // Навигация на экран с документацией
            composable(NavigationItem.Documents.route) {
                activeItem.value = NavigationItem.Documents.selectionNavbar
                val documentScreen = DocumentScreen(documentTags, documents)
                documentScreen.Screen()
            }


            // Навигация на экран с мессенджером
            composable(NavigationItem.Messenger.route) {
                activeItem.value = NavigationItem.Messenger.selectionNavbar
                navigationController.navigate(NavigationItem.Messenger.route)
                if (userProfile != null) {
                    val messengerScreen = MessengerScreen(navigationController, userMessengers)
                    messengerScreen.Screen(onMessengerChange = { selectedMessenger = it })
                }
            }

            // Навигация на экран переписки с пользователем
            composable(NavigationItem.Communication.route) {
                activeItem.value = NavigationItem.Communication.selectionNavbar
                val messenger = userMessengers.first { it == selectedMessenger }
                val communicationScreen = CommunicationScreen(messenger)
                communicationScreen.Screen { message ->
                    userMessengers.first { it == selectedMessenger }.listMessage.add(
                        MessagePrototype(
                            LocalDate.now().toString(),
                            "${LocalTime.now().hour}.${LocalTime.now().minute}",
                            message,
                            selectedMessenger!!.id!!,
                            true
                        )
                    )
                    onListMessenger(userMessengers)
                }
            }

            // Навигация на экран с добавлением пользователей
            composable(NavigationItem.AddUser.route) {
                activeItem.value = NavigationItem.AddUser.selectionNavbar
                val addUserScreen = AddUserScreen(navigationController, allUsers, profileTags)
                addUserScreen.Screen(onUserChange = { selectedUser = it })
            }
            // Навигация на экран с информацией о пользователе
            composable(NavigationItem.ProfileInfo.route) {
                activeItem.value = NavigationItem.ProfileInfo.selectionNavbar
                if (userProfile != null) {
                    val profileInfo = ProfileInfo(
                        navigationController,
                        allUsers.first { it.id == selectedUser },
                        userProfile
                    )
                    profileInfo.Screen {
                        selectedMessenger = it
                        userMessengers.add(it)
                        onListMessenger(userMessengers)
                        navigationController.navigate(NavigationItem.Communication.route)
                    }
                }
            }

            // Навигация на экран с профилем
            composable(NavigationItem.Profile.route) {
                activeItem.value = NavigationItem.Profile.selectionNavbar
                if (userProfile != null) {
                    val profileScreen =
                        ProfileScreen(navigationController, userProfile, profileTags)
                    profileScreen.Screen {
                        onProfileChange(null)
                        navigationController.navigate(NavigationItem.Login.route)
                    }
                }
            }
            // Навигация на экран с входом пользователем
            composable(NavigationItem.Login.route) {
                activeItem.value = NavigationItem.Login.selectionNavbar
                val loginScreen = LoginScreen(navigationController)
                loginScreen.Screen { onProfileChange(it) }
            }
            // Навигация на экран с регистрацией пользователя
            composable(NavigationItem.Registration.route) {
                activeItem.value = NavigationItem.Registration.selectionNavbar
                val registrationScreen = RegistrationScreen(navigationController)
                registrationScreen.Screen { registrationData = it }
            }
            // Навигация на экран с кодом для регистрации
            composable(NavigationItem.VerificationRegistration.route) {
                activeItem.value = NavigationItem.VerificationRegistration.selectionNavbar
                if (registrationData != null) {
                    val verificationRegistrationScreen =
                        VerificationRegistrationScreen(navigationController, registrationData!!)
                    verificationRegistrationScreen.Screen { onProfileChange(it) }
                }
            }
            // Навигация на экран с восстановлением пароля
            composable(NavigationItem.ForgotPassword.route) {
                activeItem.value = NavigationItem.ForgotPassword.selectionNavbar
                val forgotPasswordScreen = ForgotPasswordScreen(navigationController)
                forgotPasswordScreen.Screen(
                    onNumberPhoneChange = { forgotNumberPhone = it },
                    onForgotCodeChange = { forgotCode = it }
                )
            }
            // Навигация на экран с восстановлением пароля(через email)
            composable(NavigationItem.AnotherForgotPassword.route) {
                activeItem.value = NavigationItem.AnotherForgotPassword.selectionNavbar
                val anotherForgotPasswordScreen = AnotherForgotPasswordScreen(navigationController)
                anotherForgotPasswordScreen.Screen(
                    onEmailChange = { forgotEmail = it },
                    onForgotCodeChange = { forgotCode = it }
                )
            }
            // Навигация на экран с кодом для восстановления пароля
            composable(NavigationItem.ForgotCode.route) {
                activeItem.value = NavigationItem.ForgotCode.selectionNavbar
                val forgotCodeScreen = ForgotCodeScreen(navigationController, forgotCode)
                forgotCodeScreen.Screen()
            }
            // Навигация на экран с заменой пароля при восстановлении пароля
            composable(NavigationItem.NewPassword.route) {
                activeItem.value = NavigationItem.NewPassword.selectionNavbar
                val newPasswordScreen =
                    NewPasswordScreen(navigationController, forgotEmail, forgotNumberPhone)
                newPasswordScreen.Screen()
            }


            // Навигация на экран с заменой персонального имени
            composable(NavigationItem.ChangePersonalName.route) {
                activeItem.value = NavigationItem.ChangePersonalName.selectionNavbar

                var isValidPersonalName by remember { mutableStateOf(true) }
                val title = "Имя пользователя"
                val label = "Задать пользовательское имя"
                val helpText =
                    "Вы можете задать публичное пользовательское имя. При помощи этого имени вас легко смогут найти пользователи и связаться с вами.\n\nПользовательское имя должно быть не менее 5 символов. Пользовательское имя может содержать символы a-z, 0-9 и подчёркивания."
                val placeholder = "Имя пользователя"
                var validationPersonalName by remember { mutableStateOf(true) }

                val basicChangingInfoScreen = BasicChangingInfoScreen(navigationController)
                basicChangingInfoScreen.Screen(
                    titleAttribute = title,
                    onValueChange = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            val personalName =
                                profileRequestServices.updatePersonalNameUsingEmail(
                                    userProfile!!.email,
                                    it
                                )
                            if (personalName != null) {
                                userProfile.personalName = it
                                navigationController.navigate(NavigationItem.Profile.route)
                            }
                        }
                    },
                    conditionValue = validationPersonalName,
                    helpText = helpText,
                    label = label,
                    placeholder = placeholder,
                    onIntermediateValueChange = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            val profileByPersonalName =
                                profileRequestServices.getProfileUsingPersonalName(it)
                            isValidPersonalName =
                                !(profileByPersonalName != null && it.isNotEmpty() && profileByPersonalName != userProfile)
                            validationPersonalName =
                                Regex("^[a-zA-z0-9_]+$").matches(it) && isValidPersonalName
                        }
                    }
                )
            }
            // Навигация на экран с заменой информации о пользователе
            composable(NavigationItem.ChangePersonalInfo.route) {
                activeItem.value = NavigationItem.ChangePersonalInfo.selectionNavbar

                val title = "О себе"
                val label = "Расскажите о себе"
                val helpText =
                    "Здесь вы можете кратко написать о себе. Эта информация будет видна пользователям, которые зайдут в ваш профиль."
                val placeholder = "О себе"

                val basicChangingInfoScreen = BasicChangingInfoScreen(navigationController)
                basicChangingInfoScreen.Screen(
                    titleAttribute = title,
                    onValueChange = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            val personalInfo =
                                profileRequestServices.updatePersonalInfoUsingEmail(
                                    userProfile!!.email,
                                    it
                                )
                            if (personalInfo != null) {
                                userProfile.personalInfo = it
                                navigationController.navigate(NavigationItem.Profile.route)
                            }
                        }
                    },
                    conditionValue = true,
                    helpText = helpText,
                    label = label,
                    placeholder = placeholder,
                    onIntermediateValueChange = {},
                    maxWords = 120,
                    counter = true,
                    singleLine = false
                )
            }
            // Навигация на экран с заменой номера телефона
            composable(NavigationItem.ChangeNumberPhone.route) {
                activeItem.value = NavigationItem.ChangeNumberPhone.selectionNavbar

                val phoneNumberChangingScreen = PhoneNumberChangingScreen(navigationController)
                phoneNumberChangingScreen.Screen {
                    CoroutineScope(Dispatchers.Main).launch {
                        val profileRequestServices = ProfileRequestServicesImpl()
                        val phoneNumber = profileRequestServices.updateNumberPhoneUsingEmail(
                            userProfile!!.email,
                            it
                        )
                        if (phoneNumber != null) {
                            userProfile.telephoneNumber = it
                            navigationController.navigate(NavigationItem.Profile.route)
                        }
                    }
                }
            }
            // Навигация на экран с заменой email
            composable(NavigationItem.ChangeEmail.route) {
                activeItem.value = NavigationItem.ChangeEmail.selectionNavbar

                var isValidEmail by remember { mutableStateOf(true) }
                val title = "Email"
                val label = "Напишите свой email"
                val helpText = "Здесь вы можете сменить свой адрес электронной почты."
                val placeholder = "ivan.ivanov@gmail.com"

                val validation = Validation()
                var validationEmail by remember { mutableStateOf(true) }

                val basicChangingInfoScreen = BasicChangingInfoScreen(navigationController)
                basicChangingInfoScreen.Screen(
                    titleAttribute = title,
                    onValueChange = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            val email =
                                profileRequestServices.updateEmailUsingOldEmail(
                                    userProfile!!.email,
                                    it
                                )
                            if (email != null) {
                                userProfile.email = it
                                navigationController.navigate(NavigationItem.Profile.route)
                            }
                        }
                    },
                    conditionValue = validationEmail,
                    helpText = helpText,
                    label = label,
                    placeholder = placeholder,
                    onIntermediateValueChange = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val profileRequestServices = ProfileRequestServicesImpl()
                            val profileByEmail = profileRequestServices.getProfileUsingEmail(it)
                            isValidEmail =
                                !(profileByEmail != null && it.isNotEmpty() && profileByEmail != userProfile)
                            validationEmail = validation.isValidEmail(it) && isValidEmail
                        }
                    }
                )
            }
            // Навигация на экран с заменой пароля
            composable(NavigationItem.ChangePassword.route) {
                activeItem.value = NavigationItem.ChangePassword.selectionNavbar

                val replacePasswordScreen = ReplacePasswordScreen(navigationController)
                replacePasswordScreen.Screen {
                    CoroutineScope(Dispatchers.Main).launch {
                        val password: String? =
                            ProfileRequestServicesImpl().updatePasswordUsingEmail(
                                userProfile!!.email,
                                it.first,
                                it.second
                            )
                        if (password != null) {
                            println("Пароль успешно обновлён")
                        }
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
                    Navbar(navigationController = navigationController)
                }
            }
        }
    }
}


