package com.example.documentsearch.patterns

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.delay


/**
 * Функция предназначена для отображения таймера для повторной отправки кода
 * @param onResendClicked Обработчик клика после таймера
 */
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
            textDecoration = if ((timerState.intValue == 0)) TextDecoration.Underline else TextDecoration.None
        )
    )
}