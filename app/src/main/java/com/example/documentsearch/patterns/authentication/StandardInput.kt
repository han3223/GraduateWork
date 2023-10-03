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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText


/**
 * Функция отображает стандартную форму для приложения
 * @param value Значение для отображения
 * @param onValueChanged Обработчик, который возвращает значение из формы
 * @param mainBoxModifier Внешний вид для контейнера
 * @param textFieldModifier Внешний вид текстового поля
 * @param keyboardOptions Модификации для клавиатуры
 * @param keyboardActions Обработчик событий клавиатуры
 * @param visualTransformation Трансформация введённых символов символов
 * @param label Лейбел для формы
 * @param styleLabel Стиль лейбла
 * @param placeholder Подсказка для ввода
 * @param validColor Цвет валидации для формы
 * @param invalidList Список подсказок для пользователя в случе если он ввёл что-то неправильно
 * @param activeTextField Активное ли текстовое поле изначально?
 * @param textStyle Стиль текста в текстовом поле
 * @param invalidList Список для валидации
 * @param singleLine Однострочное текстовое поле?
 * @param counter Ограничение на количество символов в текстовом поле
 * @param count Максимальное количество символов в текстовом поле
 * @param isCheckValue Проверка значения в текстовом поле
 */
@Composable
fun StandardInput(
    value: String,
    onValueChanged: (String) -> Unit,
    mainBoxModifier: Modifier,
    textFieldModifier: Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String,
    styleLabel: TextStyle = TextStyle(
        fontSize = 17.sp,
        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
        fontWeight = FontWeight(600),
        color = TextColor,
    ),
    placeholder: String,
    validColor: Color = Color.White,
    invalidList: List<ValidationText> = listOf(),
    activeTextField: Boolean = true,
    textStyle: TextStyle = TextStyle(color = TextColor, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.montserrat_semi_bold))),
    singleLine: Boolean = true,
    counter: Boolean = false,
    count: Int = 0,
    isCheckValue: Boolean = false
) {
    val focused = remember { mutableStateOf(false) }
    Box(
        modifier = mainBoxModifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = label,
                style = styleLabel
            )
            StandardTextField(
                value = value,
                onValueChanged = { onValueChanged(it) },
                modifier = textFieldModifier,
                placeholder = placeholder,
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
                validColor = validColor,
                onFocusChange = { focused.value = it },
                keyboardActions = keyboardActions,
                activeTextField = activeTextField,
                textStyle = textStyle,
                singleLine = singleLine,
                counter = counter,
                count = count,
                isCheckValue = isCheckValue
            )
        }
    }

    // Блок с ошибкой валидации (текст с ошибкой валидации)
    AnimatedVisibility(
        visible = focused.value,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            invalidList.forEach {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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