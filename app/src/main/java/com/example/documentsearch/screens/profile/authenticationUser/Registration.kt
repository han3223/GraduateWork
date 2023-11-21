package com.example.documentsearch.screens.profile.authenticationUser

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.ProfilesRequests
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Форма для регистрации пользователя
 * @param navController Контроллер для навигации
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Registration(navController: NavHostController, onProfileDataChange: (Profile) -> Unit) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") } // Имя
    var lastName by remember { mutableStateOf("") } // Фамилия
    var patronymic by remember { mutableStateOf("") } // Отчество
    var numberPhone by remember { mutableStateOf("") } // Номер телефона
    var email by remember { mutableStateOf("") } // Email
    var password by remember { mutableStateOf("") } // Пароль
    var repeatPassword by remember { mutableStateOf("") } // Повтор пароля

    val keyboardController = LocalSoftwareKeyboardController.current // Контроллер клавиатуры
    val firstNameFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для имени пользователя
    val lastNameFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для фамилии пользователя
    val patronymicFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для отчества пользователя
    val numberPhoneFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для номера телефона пользователя
    val emailFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для email пользователя
    val passwordFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для пароля пользователя
    val repeatPasswordFocusRequester =
        remember { FocusRequester() } // Обработчик фокуса для повтора пароля

    val validation = Validation() // Класс для валидации

    // Текст для валидации пароля
    val passwordValidationText = listOf(
        ValidationText(validation.isMinLength(password), "Минимум 8 символов"),
        ValidationText(validation.isWhitespace(password), "Не допускаются пробелы"),
        ValidationText(validation.isLowerCase(password), "Минимум один строчной символ"),
        ValidationText(validation.isUpperCase(password), "Минимум один заглавный символ"),
        ValidationText(validation.isDigit(password), "Минимум одна цифра"),
        ValidationText(validation.isSpecialCharacter(password), "Минимум один специальный символ"),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = rememberLazyListState()
    ) {
        item(0) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .zIndex(2f)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 33.dp))
                    .background(color = MainColorLight),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Регистрация",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 30.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Имя
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        StandardInput(
                            value = firstName,
                            label = "Имя*:",
                            placeholder = "Иван",
                            onValueChanged = {
                                firstName = it
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            validColor = if (firstName.any { it.isDigit() }) Color.Red else TextColor,
                            invalidList = if (firstName.isEmpty()) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле не должно быть пустым"
                                    )
                                )
                            } else if (firstName.any { it.isDigit() }) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле должно содержать только символы алфавита"
                                    )
                                )
                            } else listOf(),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    lastNameFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(firstNameFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Фамилия
                        StandardInput(
                            value = lastName,
                            label = "Фамилия*:",
                            placeholder = "Иванов",
                            onValueChanged = {
                                lastName = it
                            },
                            validColor = if (lastName.any { it.isDigit() }) Color.Red else TextColor,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            invalidList = if (lastName.isEmpty()) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле не должно быть пустым"
                                    )
                                )
                            } else if (lastName.any { it.isDigit() }) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле должно содержать только символы алфавита"
                                    )
                                )
                            } else listOf(),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    patronymicFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(lastNameFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Отчество
                        StandardInput(
                            value = patronymic,
                            label = "Отчество:",
                            placeholder = "Иванович",
                            onValueChanged = {
                                patronymic = it
                            },
                            validColor = if (patronymic.any { it.isDigit() }) Color.Red else TextColor,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            invalidList =
                            if (patronymic.any { it.isDigit() }) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле должно содержать только символы алфавита"
                                    )
                                )
                            } else listOf(),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    numberPhoneFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(patronymicFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Номер телефона
                        PhoneInput(
                            phoneNumber = numberPhone,
                            label = "Номер телефона*",
                            onPhoneNumberChanged = {
                                numberPhone = it
                            },
                            validColor = if (validation.isValidPhone(numberPhone) && numberPhone.length == 11 || numberPhone.isEmpty()) TextColor else Color.Red,
                            invalidList = if (numberPhone.isEmpty()) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Поле не должно быть пустым"
                                    )
                                )
                            } else if (!validation.isValidPhone(numberPhone) || numberPhone.length != 11) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Номер телефона должен использовать международный формат"
                                    )
                                )
                            } else listOf(),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    emailFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(numberPhoneFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Email
                        StandardInput(
                            value = email,
                            label = "Email*:",
                            placeholder = "ivan.ivanov@gmail.com",
                            onValueChanged = {
                                email = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            validColor = if (validation.isValidEmail(email) || email.isEmpty()) TextColor else Color.Red,
                            invalidList = if (!validation.isValidEmail(email)) {
                                listOf(
                                    ValidationText(
                                        null,
                                        "Email должен содержать общепринятый формат"
                                    )
                                )
                            } else listOf(),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    passwordFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(emailFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Пароль
                        StandardInput(
                            value = password,
                            label = "Пароль*:",
                            placeholder = "*********",
                            onValueChanged = {
                                password = it
                            },
                            visualTransformation = PasswordVisualTransformation('*'),
                            validColor = if (validation.isValidPassword(password) || password.isEmpty()) TextColor else Color.Red,
                            invalidList = passwordValidationText,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    repeatPasswordFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(passwordFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { },
                            isCheckValue = true
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        // Повторите пароль
                        StandardInput(
                            value = repeatPassword,
                            label = "Повторите пароль*:",
                            placeholder = "*********",
                            onValueChanged = { repeatPassword = it },
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
                                    repeatPasswordFocusRequester.freeFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(repeatPasswordFocusRequester)
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { },
                            isCheckValue = true
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(AdditionalColor)
                        )
                        Button(
                            enabled = firstName.isNotEmpty() &&
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
                                    password == repeatPassword,
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val checkProfileByEmail =
                                        ProfilesRequests().getProfileByEmail(email)
                                    val checkProfileByPhoneNumber =
                                        ProfilesRequests().getProfileByPhoneNumber(numberPhone)

                                    if (checkProfileByEmail == null && checkProfileByPhoneNumber == null) {
                                        onProfileDataChange(
                                            Profile(
                                                lastName = lastName,
                                                firstName = firstName,
                                                patronymic = patronymic,
                                                telephoneNumber = numberPhone,
                                                email = email,
                                                password = password
                                            )
                                        )
                                        navController.navigate(NavigationItem.VerificationRegistration.route)
                                    } else
                                        Toast.makeText(
                                            context,
                                            "Такой пользователь уже существет!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                }
                                /*TODO(Сделать рассылку кода пользователю для регистрации)*/
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
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                        }
                        Text(
                            text = "Войти",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontWeight = FontWeight(100),
                                color = TextColor,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 30.dp)
                                .clickable { navController.navigate(NavigationItem.Login.route) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}