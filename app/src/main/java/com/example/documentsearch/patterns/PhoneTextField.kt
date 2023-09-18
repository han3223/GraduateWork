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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает базовый функционал текстового поля для номера телефона
 * @param phoneNumber Номер телефона, который будет отображаться в поле
 * @param onPhoneNumberChanged Обработчик, который возвращает номер телефона из текстового поля
 * @param modifier Внешний вид текстового поля
 * @param keyboardActions Обработчик событий клавиатуры
 * @param onFocusChange Обработчик фокуса текстового поля
 * @param mask Маска для номера телефона
 * @param maskNumber Символ, который будет меняться в маске
 * @param validColor Цвет валидации текстового поля
 */
@Composable
fun PhoneTextField(
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions,
    onFocusChange: (Boolean) -> Unit,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
    validColor: Color,
) {
    BasicTextField(
        value = phoneNumber,
        onValueChange = { value -> onPhoneNumberChanged(value.take(mask.count { it == maskNumber })) },
        modifier = modifier.onFocusChanged { onFocusChange(it.isFocused) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        textStyle = TextStyle(color = TextColor, fontSize = 16.sp),
        singleLine = true,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            if (phoneNumber.isEmpty()) {
                Text(
                    text = "+7 (999) 999-99-99",
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


/**
 * Класс предназначен для работы с маской формы
 * @param mask Маска телефона
 * @param maskNumber Символ замены в маске
 */
class PhoneVisualTransformation(private val mask: String, private val maskNumber: Char) :
    VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }


    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}


/**
 * Класс представляет собой маппер для маски
 * @param mask Маска телефона
 * @param numberChar Символ замены в маске
 */
private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}