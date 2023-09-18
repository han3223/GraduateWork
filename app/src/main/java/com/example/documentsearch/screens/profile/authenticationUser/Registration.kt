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
import androidx.compose.ui.graphics.Color
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
import com.example.documentsearch.R
import com.example.documentsearch.patterns.PhoneInput
import com.example.documentsearch.patterns.StandardInput
import com.example.documentsearch.screens.profile.profileScreen
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText


/**
 * Форма для регистрации пользователя
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Registration() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var numberPhone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val validation = Validation()
    val passwordValidationText = listOf(
        ValidationText(validation.isMinLenght(password), "Минимум 8 символов"),
        ValidationText(validation.isWhitespace(password), "Не допускаются пробелы"),
        ValidationText(validation.isLowerCase(password), "Минимум один строчной символ"),
        ValidationText(validation.isUpperCase(password), "Минимум один заглавный символ"),
        ValidationText(validation.isDigit(password), "Минимум одна цифра"),
        ValidationText(validation.isSpecialCharacter(password), "Минимум один специальный символ"),
    )

    val keyboardController = LocalSoftwareKeyboardController.current
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val patronymicFocusRequester = remember { FocusRequester() }
    val numberPhoneFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val repeatPasswordFocusRequester = remember { FocusRequester() }

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
                            modifier = Modifier
                                .focusRequester(firstNameFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(lastNameFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(patronymicFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(numberPhoneFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(emailFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(passwordFocusRequester)
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
                            modifier = Modifier
                                .focusRequester(repeatPasswordFocusRequester)
                        )
                        Button(
                            onClick = {
                                profileScreen.value = "verify registration"
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
                                .clickable { profileScreen.value = "login" }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}