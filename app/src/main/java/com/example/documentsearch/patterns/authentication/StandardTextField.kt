package com.example.documentsearch.patterns.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.AdditionalColor
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
 * @param activeTextField Активное ли текстовое поле изначально?
 * @param textStyle Стиль текста в текстовом поле
 * @param singleLine Однострочное текстовое поле?
 * @param counter Ограничение на количество символов в текстовом поле
 * @param count Максимальное количество символов в текстовом поле
 * @param isCheckValue Проверка значения в текстовом поле
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
    activeTextField: Boolean,
    textStyle: TextStyle,
    singleLine: Boolean,
    counter: Boolean = false,
    count: Int = -1,
    isCheckValue: Boolean = false,
    alwaysDisable: Boolean = false,
    modifierText: Modifier = Modifier
) {
    var active by remember { mutableStateOf(true) } // Активно ли сейчас текстовое поле
    var visualTransform by remember { mutableStateOf(visualTransformation) } // Визуальная трансформация
    BasicTextField(
        value = if (value.length == count + 1 && counter) value.removeRange(
            value.length - 1,
            value.length
        ) else value,
        onValueChange = {
            onValueChanged(it)
        },
        modifier = modifier.onFocusChanged {
            onFocusChange(it.isFocused)
            active = it.isFocused
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransform,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = modifierText.weight(1f)) {
                        innerTextField()
                    }
                    if (visualTransformation != VisualTransformation.None && value.isNotEmpty() && isCheckValue) {
                        var visibleValue by remember { mutableStateOf(false) }
                        Icon(
                            painter = painterResource(id = if (visibleValue) R.drawable.invisible_password else R.drawable.visible_password),
                            contentDescription = null,
                            modifier = Modifier
                                .width(15.dp)
                                .align(Alignment.CenterVertically)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    visualTransform = if (!visibleValue)
                                        VisualTransformation.None
                                    else
                                        visualTransformation
                                    visibleValue = !visibleValue
                                },
                            tint = TextColor
                        )
                    }
                    if (counter) {
                        Text(
                            text = if (count - value.length < 0) "0" else (count - value.length).toString(),
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                fontWeight = FontWeight(500),
                                color = AdditionalColor,
                                textAlign = TextAlign.End
                            ),
                            modifier = Modifier.width(30.dp)
                        )
                    }
                }

                if (activeTextField || active && !alwaysDisable) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(validColor)
                    )
                }
            }
        },
        cursorBrush = SolidColor(TextColor),
    )
}