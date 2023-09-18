package com.example.documentsearch.patterns

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.ui.theme.TextColor


/**
 * Функция отображает базовый функционал текстового поля
 * @param value Значение, который будет отображаться в поле
 * @param onValueChanged Обработчик, который возвращает значение из формы
 * @param modifier Внешний вид текстового поля
 * @param keyboardOptions Модификация для клавиатуры
 * @param keyboardActions Обработчик событий клавиатуры
 * @param visualTransformation Трансформация введённых символов символов
 * @param onFocusChange Обработчик фокуса текстового поля
 * @param placeholder Подсказака для пользователя
 * @param validColor Цвет валидации текстового поля
 */
@Composable
fun StandardTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChange: (Boolean) -> Unit,
    placeholder: String = "",
    validColor: Color,
) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        modifier = modifier.onFocusChanged { onFocusChange(it.isFocused) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(color = TextColor, fontSize = 16.sp),
        singleLine = true,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 5.dp)
                ) {
                    innerTextField()
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(validColor)
                )
            }
        },
        cursorBrush = SolidColor(TextColor)
    )
}