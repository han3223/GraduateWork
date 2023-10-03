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
import com.example.documentsearch.validation.ValidationText

/**
 * Стандартный экран смены данных пользователя
 * @param value Значение для смены данных
 * @param valueChanged Обработчик смены данных
 * @param helpText Вспомогательный текст
 * @param label Лейбел для текстового поля
 * @param placeholder Подсказка в текстовом поле
 * @param singleLine Однострочное текстовое поле?
 * @param conditionValidation Нужна ли валидация для текстового поля
 * @param invalidList Список для валидации
 * @param counter Ограничение на количество символов в текстовом поле
 * @param count Максимальное количество символов в текстовом поле
 */
@Composable
fun StandardChangingInfoScreen(
    value: String,
    valueChanged: (String) -> Unit,
    helpText: String,
    label: String,
    placeholder: String,
    singleLine: Boolean = true,
    conditionValidation: Boolean = true,
    invalidList: List<ValidationText> = listOf(),
    counter: Boolean = false,
    count: Int = 0
    ) {
    Column(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .height(10000.dp)
            .background(color = MainColorLight),
    ) {
        StandardInput(
            value = value,
            onValueChanged = { valueChanged(it) },
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, if(singleLine) 5.dp else 15.dp),
            textFieldModifier =
            if (singleLine)
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(color = Color.Transparent)
                    .onFocusChanged { }
            else
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .onFocusChanged { },
            keyboardActions = KeyboardActions.Default,
            label = label,
            placeholder = placeholder,
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
            singleLine = singleLine,
            validColor = if (conditionValidation) TextColor else Color.Red,
            invalidList = invalidList,
            counter = counter,
            count = count
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )
        Text(
            text = helpText,
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