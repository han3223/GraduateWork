package com.example.documentsearch.patterns.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText


/**
 * Функция отображает стандартную форму номера телефона для приложения
 * @param phoneNumber Номер телефона для отображения
 * @param onPhoneNumberChanged Обработчик, который возвращает значение из формы
 * @param mainBoxModifier Внешний вид контенера
 * @param textFieldModifier Внешний вид текстового поля
 * @param keyboardActions Обработчик событий клавиатуры
 * @param label Лейбел для формы
 * @param styleLabel Стиль для лейбла
 * @param validColor Цвет валидации для формы
 * @param invalidList Список подсказок для пользователя в случе если он ввёл что-то неправильно
 * @param activeTextField Активно ли текстовое поле изначально
 * @param textStyle Стиль текста в текстовом поле
 */
@Composable
fun PhoneInput(
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    mainBoxModifier: Modifier,
    textFieldModifier: Modifier,
    keyboardActions: KeyboardActions,
    label: String,
    styleLabel: TextStyle = TextStyle(
        fontSize = 17.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
        fontWeight = FontWeight(600),
        color = TextColor,
    ),
    validColor: Color = Color.White,
    invalidList: List<ValidationText> = listOf(),
    activeTextField: Boolean = true,
    textStyle: TextStyle = TextStyle(color = TextColor, fontSize = 16.sp)
) {
    val focused = remember { mutableStateOf(false) }

    Box(
        modifier = mainBoxModifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = label,
                style = styleLabel,
            )
            PhoneTextField(phoneNumber,
                mask = "+0 (000) 000-00-00",
                maskNumber = '0',
                onPhoneNumberChanged = { onPhoneNumberChanged(it) },
                modifier = textFieldModifier,
                validColor = validColor,
                onFocusChange = { focused.value = it },
                keyboardActions = keyboardActions,
                activeTextField = activeTextField,
                textStyle = textStyle
            )
        }
    }
    AnimatedVisibility(
        visible = focused.value,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        modifier = Modifier.padding(35.dp, 0.dp, 35.dp, 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            invalidList.forEach {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (it.invalid != null) {
                        Icon(
                            imageVector = if (it.invalid!!) Icons.Default.Close else Icons.Default.Check,
                            contentDescription = null,
                            tint = if (it.invalid!!) Color.Red else Color.Green,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = it.text,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                    )
                }
            }
        }
    }
}