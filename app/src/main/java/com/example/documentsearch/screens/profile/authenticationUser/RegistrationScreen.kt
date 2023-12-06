package com.example.documentsearch.screens.profile.authenticationUser

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_UNDERLINE_TEXT
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationScreen : HeadersProfile(), Screen {
    private val validation = Validation()

    @Composable
    override fun Content() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Body() {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var patronymic by remember { mutableStateOf("") }
        var numberPhone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current // Контроллер клавиатуры
        val firstNameFocusRequester = remember { FocusRequester() }
        val lastNameFocusRequester = remember { FocusRequester() }
        val patronymicFocusRequester = remember { FocusRequester() }
        val numberPhoneFocusRequester = remember { FocusRequester() }
        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }
        val repeatPasswordFocusRequester = remember { FocusRequester() }

        val enabledButtonRegistration = firstName.isNotEmpty() &&
                !firstName.any { it.isDigit() } &&
                lastName.isNotEmpty() &&
                !lastName.any { it.isDigit() } &&
                !patronymic.any { it.isDigit() } &&
                validation.isValidPhone(numberPhone) &&
                numberPhone.length == 11 &&
                validation.isValidEmail(email) &&
                !validation.isMinLength(password) &&
                !validation.isWhitespace(password) &&
                !validation.isLowerCase(password) &&
                !validation.isUpperCase(password) &&
                !validation.isDigit(password) &&
                !validation.isSpecialCharacter(password) &&
                password == repeatPassword

        val userPrototype = UserProfilePrototype(
            lastName = lastName,
            firstName = firstName,
            patronymic = patronymic,
            telephoneNumber = numberPhone,
            email = email,
            password = password
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = rememberLazyListState()
        ) {
            item(0) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 33.dp))
                        .background(color = MainColorLight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Регистрация",
                        style = MAXIMUM_TEXT,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 30.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Separator()
                        FirstName(
                            firstName = firstName,
                            currentRequester = firstNameFocusRequester,
                            nextRequester = lastNameFocusRequester,
                        ) { firstName = it }

                        Separator()
                        LastName(
                            lastName = lastName,
                            currentRequester = lastNameFocusRequester,
                            nextRequester = patronymicFocusRequester,
                        ) { lastName = it }

                        Separator()
                        Patronymic(
                            patronymic = patronymic,
                            currentRequester = patronymicFocusRequester,
                            nextRequester = numberPhoneFocusRequester,
                        ) { patronymic = it }

                        Separator()
                        NumberPhone(
                            numberPhone = numberPhone,
                            currentRequester = numberPhoneFocusRequester,
                            nextRequester = emailFocusRequester,
                        ) { numberPhone = it }

                        Separator()
                        Email(
                            email = email,
                            currentRequester = emailFocusRequester,
                            nextRequester = passwordFocusRequester,
                        ) { email = it }

                        Separator()
                        Password(
                            password = password,
                            currentRequester = passwordFocusRequester,
                            nextRequester = repeatPasswordFocusRequester,
                        ) { password = it }

                        Separator()
                        RepeatPassword(
                            password = password,
                            repeatPassword = repeatPassword,
                            currentRequester = repeatPasswordFocusRequester,
                            keyboardController = keyboardController,
                        ) { repeatPassword = it }

                        Separator()
                        ButtonSignIn(enabledButtonRegistration, userPrototype)

                        NavigateLogIn()
                    }
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    @Composable
    private fun FirstName(
        firstName: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onFirstNameChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Имя*:",
            placeholder = "Иван",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            validColor = if (firstName.any { it.isDigit() }) Color.Red else TextColor,
            invalidList = if (firstName.isEmpty()) {
                listOf(ValidationText(null, "Поле не должно быть пустым"))
            } else if (firstName.any { it.isDigit() }) {
                listOf(
                    ValidationText(null, "Поле должно содержать только символы алфавита")
                )
            } else listOf(),
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() }),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
        )

        standardInput.Input(value = firstName, onValueChanged = { onFirstNameChange(it) })
    }

    @Composable
    private fun LastName(
        lastName: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onLastNameChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Фамилия*:",
            placeholder = "Иванов",
            validColor = if (lastName.any { it.isDigit() }) Color.Red else TextColor,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            invalidList = if (lastName.isEmpty()) {
                listOf(ValidationText(null, "Поле не должно быть пустым"))
            } else if (lastName.any { it.isDigit() }) {
                listOf(
                    ValidationText(null, "Поле должно содержать только символы алфавита")
                )
            } else listOf(),
            keyboardActions = KeyboardActions(
                onDone = {
                    nextRequester.requestFocus()
                }
            ),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { }
        )

        standardInput.Input(value = lastName, onValueChanged = { onLastNameChange(it) })
    }

    @Composable
    private fun Patronymic(
        patronymic: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onPatronymicChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Отчество:",
            placeholder = "Иванович",
            validColor = if (patronymic.any { it.isDigit() }) Color.Red else TextColor,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            ),
            invalidList =
            if (patronymic.any { it.isDigit() }) {
                listOf(
                    ValidationText(null, "Поле должно содержать только символы алфавита")
                )
            } else listOf(),
            keyboardActions = KeyboardActions(
                onDone = {
                    nextRequester.requestFocus()
                }
            ),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
        )

        standardInput.Input(value = patronymic, onValueChanged = { onPatronymicChange(it) })
    }

    @Composable
    private fun NumberPhone(
        numberPhone: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onNumberPhoneChange: (String) -> Unit
    ) {
        val phoneInput = PhoneInput(
            label = "Номер телефона*",
            validColor = if (validation.isValidPhone(numberPhone) && numberPhone.length == 11 || numberPhone.isEmpty()) TextColor else Color.Red,
            invalidList = if (numberPhone.isEmpty()) {
                listOf(
                    ValidationText(null, "Поле не должно быть пустым")
                )
            } else if (!validation.isValidPhone(numberPhone) || numberPhone.length != 11) {
                listOf(
                    ValidationText(null, "Номер телефона должен использовать международный формат")
                )
            } else listOf(),
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() }),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { }
        )

        phoneInput.Input(
            phoneNumber = numberPhone,
            onPhoneNumberChanged = { onNumberPhoneChange(it) })
    }

    @Composable
    private fun Email(
        email: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onEmailChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Email*:",
            placeholder = "ivan.ivanov@gmail.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            validColor = if (validation.isValidEmail(email) || email.isEmpty()) TextColor else Color.Red,
            invalidList = if (!validation.isValidEmail(email)) {
                listOf(
                    ValidationText(null, "Email должен содержать общепринятый формат")
                )
            } else listOf(),
            keyboardActions = KeyboardActions(
                onDone = { nextRequester.requestFocus() }
            ),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
        )

        standardInput.Input(value = email, onValueChanged = { onEmailChange(it) })
    }

    @Composable
    private fun Password(
        password: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onPasswordChange: (String) -> Unit
    ) {
        val passwordValidationText = listOf(
            ValidationText(validation.isMinLength(password), "Минимум 8 символов"),
            ValidationText(validation.isWhitespace(password), "Не допускаются пробелы"),
            ValidationText(validation.isLowerCase(password), "Минимум один строчной символ"),
            ValidationText(validation.isUpperCase(password), "Минимум один заглавный символ"),
            ValidationText(validation.isDigit(password), "Минимум одна цифра"),
            ValidationText(
                validation.isSpecialCharacter(password),
                "Минимум один специальный символ"
            ),
        )

        val standardInput = StandardInput(
            label = "Пароль*:",
            placeholder = "*********",
            visualTransformation = PasswordVisualTransformation('*'),
            validColor = if (validation.isValidPassword(password) || password.isEmpty()) TextColor else Color.Red,
            invalidList = passwordValidationText,
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() }),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            isCheckValue = true
        )

        standardInput.Input(value = password, onValueChanged = { onPasswordChange(it) })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun RepeatPassword(
        password: String,
        repeatPassword: String,
        currentRequester: FocusRequester,
        keyboardController: SoftwareKeyboardController?,
        onRepeatPasswordChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Повторите пароль*:",
            placeholder = "*********",
            visualTransformation = PasswordVisualTransformation('*'),
            validColor = if (password == repeatPassword || repeatPassword.isEmpty()) TextColor else Color.Red,
            invalidList = listOf(
                ValidationText(
                    password != repeatPassword,
                    "Пароли должны совпадать"
                )
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    currentRequester.freeFocus()
                    keyboardController?.hide()
                }
            ),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(25.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            isCheckValue = true
        )

        standardInput.Input(value = repeatPassword, onValueChanged = { onRepeatPasswordChange(it) })
    }

    @Composable
    private fun ButtonSignIn(enabled: Boolean, userPrototype: UserProfilePrototype) {
        val navigator = LocalNavigator.currentOrThrow
        Button(
            enabled = enabled,
            onClick = {
                signIn(userPrototype) {
                    navigator.push(VerificationRegistrationScreen(it))
                }
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainColor,
                contentColor = TextColor
            )
        ) {
            Text(
                text = "Зарегистрироваться",
                style = HEADING_TEXT,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }

    private fun signIn(
        userPrototype: UserProfilePrototype,
        onProfileDataChange: (UserProfilePrototype) -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val profileRequestServices = ProfileRequestServicesImpl()
            val checkProfileByEmail =
                profileRequestServices.getProfileUsingEmail(userPrototype.email)
            val checkProfileByPhoneNumber =
                profileRequestServices.getProfileUsingPhoneNumber(userPrototype.telephoneNumber)

            if (checkProfileByEmail == null && checkProfileByPhoneNumber == null) {
                onProfileDataChange(userPrototype)
            }
        }
        /*TODO(Сделать рассылку кода пользователю для регистрации)*/
    }

    @Composable
    private fun NavigateLogIn() {
        val navigator = LocalNavigator.currentOrThrow
        Text(
            text = "Войти",
            style = HIGHLIGHTING_UNDERLINE_TEXT,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp)
                .clickable { navigator.push(LoginScreen()) }
        )
    }

    @Composable
    private fun Separator() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AdditionalColor)
        )
    }
}