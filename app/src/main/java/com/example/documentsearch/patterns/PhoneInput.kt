package com.example.documentsearch.patterns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText


/**
 * Функция отображает стандартную форму номера телефона для приложения
 * @param phoneNumber Номер телефона для отображения
 * @param onPhoneNumberChanged Обработчик, который возвращает значение из формы
 * @param modifier Внешний вид текстового поля
 * @param keyboardActions Обработчик событий клавиатуры
 * @param label Лейбел для формы
 * @param validColor Цвет валидации для формы
 * @param invalidList Список подсказок для пользователя в случе если он ввёл что-то неправильно
 */
@Composable
fun PhoneInput(
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    modifier: Modifier,
    keyboardActions: KeyboardActions,
    label: String,
    validColor: Color = Color.White,
    invalidList: List<ValidationText> = listOf(),
) {
    val focused = remember { mutableStateOf(false) }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AdditionalColor)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                    fontWeight = FontWeight(600),
                    color = TextColor,
                ),
            )
            PhoneTextField(phoneNumber,
                mask = "+0 (000) 000-00-00",
                maskNumber = '0',
                onPhoneNumberChanged = { onPhoneNumberChanged(it) },
                modifier = modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(color = Color.Transparent)
                    .onFocusChanged { },
                validColor = validColor,
                onFocusChange = { focused.value = it },
                keyboardActions = keyboardActions
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
    Spacer(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(AdditionalColor)
    )
}