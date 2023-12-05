package com.example.documentsearch.patterns.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.delay


class VerificationCodeInput {
    @Composable
    fun Input(onCodeEntered: (String) -> Unit) {
        val code = remember { mutableStateOf("") }
        val focusRequesters = remember { List(4) { FocusRequester() } }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (i in 0 until 4) {
                val digit = if (i < code.value.length) code.value[i].toString() else ""
                val isLastDigit = i == 3
                VerificationBlockBasicTextField(
                    value = digit,
                    modifier = Modifier.focusRequester(focusRequesters[i]),
                    onValueChange = { newDigit ->
                        val newCode = code.value.toMutableList()
                        if (i < newCode.size)
                            newCode[i] = newDigit.firstOrNull() ?: ' '
                        else
                            newCode.add(newDigit.firstOrNull() ?: ' ')

                        code.value = newCode.joinToString("")

                        if (isLastDigit && code.value.length == 4)
                            onCodeEntered(code.value)
                        else if (i < 3)
                            focusRequesters[i + 1].requestFocus()
                    }
                )
                LaunchedEffect(Unit) { focusRequesters[0].requestFocus() }
            }
        }
    }

    @Composable
    fun ResendVerificationCode(onResendClicked: () -> Unit) {
        val timerDurationInSeconds = 60
        val timerState = remember { mutableIntStateOf(timerDurationInSeconds) }

        LaunchedEffect(key1 = timerState.intValue) {
            if (timerState.intValue > 0) {
                delay(1000)
                timerState.intValue -= 1
            }
        }

        val buttonText = if (timerState.intValue > 0) {
            "Отправить код повторно через (${timerState.intValue} с)"
        } else {
            "Отправить код еще раз"
        }

        ClickableText(
            text = AnnotatedString(buttonText),
            onClick = { offset ->
                if ((timerState.intValue == 0) && (offset == 0)) {
                    timerState.intValue = timerDurationInSeconds
                    onResendClicked()
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            style = TextStyle(
                color = TextColor,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                textDecoration = if ((timerState.intValue == 0)) TextDecoration.Underline else TextDecoration.None
            )
        )
    }

    @Composable
    private fun VerificationBlockBasicTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = Color.Black, fontSize = 35.sp, fontFamily = FontFamily(
                Font(R.font.montserrat_bold)
            )
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .size(50.dp, 70.dp)
                        .background(AdditionalColor, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(start = 15.dp)
                    ) {
                        innerTextField()
                    }
                }
            },
            cursorBrush = SolidColor(Color.Black),
            modifier = modifier
        )
    }
}