package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation


/**
 * Экран для замены пароля пользователя
 * @param oldPassword Старый пароль пользователя
 * @param oldPasswordChanged Обработчик значения старого пароля в поле
 * @param newPassword Новый пароль пользователя
 * @param newPasswordChanged Обработчик значения нового пароля в поле
 * @param repeatPassword Повтор пароля
 * @param repeatPasswordChanged Обработчик значения повтора пароля в поле
 */
@Composable
fun ReplacePasswordScreen(
    oldPassword: String,
    oldPasswordChanged: (String) -> Unit,
    newPassword: String,
    newPasswordChanged: (String) -> Unit,
    repeatPassword: String,
    repeatPasswordChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .height(10000.dp)
            .background(color = MainColorLight),
    ) {
        StandardInput(
            value = oldPassword,
            onValueChanged = { oldPasswordChanged(it) },
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 5.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Старый пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = TextStyle(
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )

        StandardInput(
            value = newPassword,
            onValueChanged = { newPasswordChanged(it) },
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 0.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Новый пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = TextStyle(
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            validColor = if (Validation().isValidPassword(newPassword) || newPassword.isEmpty()) TextColor else Color.Red,
            isCheckValue = true
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )

        StandardInput(
            value = repeatPassword,
            onValueChanged = { repeatPasswordChanged(it) },
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 0.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Повторите пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            validColor = if (repeatPassword == newPassword) TextColor else Color.Red,
            isCheckValue = true
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )
        Text(
            text = "Здесь вы можете поменять свой старый пароль на новый. \n\nПароль должен содержать минимум один строчной символ, один прописной символ, одну цифру, один специальный символ, а также иметь минимум 8 символов.",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                fontWeight = FontWeight(500),
                color = AdditionalColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp, 10.dp, 7.dp, 0.dp)
        )
    }
}