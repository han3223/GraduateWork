package com.example.documentsearch.screens.profile.authenticationUser

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.patterns.StandardInput
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText


/**
 * Форма для обновления пароля пользователя
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewPassword() {
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordFocusRequester = remember { FocusRequester() }
    val repeatPasswordFocusRequester = remember { FocusRequester() }

    val validation = Validation()
    val passwordValidationText = listOf(
        ValidationText(validation.isMinLenght(password), "Минимум 8 символов"),
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
            text = "Восстановление пароля",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                color = TextColor,
            ),
            modifier = Modifier
                .padding(20.dp, 20.dp, 20.dp, 30.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Пароль
            StandardInput(
                value = password,
                label = "Пароль:",
                placeholder = "*********",
                onValueChanged = { password = it },
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
                label = "Повторите пароль:",
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
                onClick = { /*TODO(Сделать обработку входа пользователя после смены пароля)*/ },
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
                    text = "Создать пароль",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                    ),
                    modifier = Modifier.padding(vertical = 7.dp)
                )
            }
        }
    }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}