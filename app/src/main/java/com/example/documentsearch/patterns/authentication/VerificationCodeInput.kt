package com.example.documentsearch.patterns.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp


/**
 * Функция предназначена для отображения формы кода верификации
 * @param onCodeEntered Обработчик введнного кода
 */
@Composable
fun VerificationCodeInput(onCodeEntered: (String) -> Unit) {
    val code = remember { mutableStateOf("") }
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        for (i in 0 until 4) {
            val digit = if (i < code.value.length) code.value[i].toString() else ""
            val isLastDigit = i == 3
            VerificationBlockBasicTextField(
                value = digit,
                onValueChange = { newDigit ->
                    val newCode = code.value.toMutableList()
                    if (i < newCode.size) {
                        newCode[i] = newDigit.firstOrNull() ?: ' '
                    } else {
                        newCode.add(newDigit.firstOrNull() ?: ' ')
                    }
                    code.value = newCode.joinToString("")
                    if (isLastDigit && code.value.length == 4) {
                        onCodeEntered(code.value)
                    } else if (i < 3) {
                        focusRequesters[i + 1].requestFocus()
                    }
                },
                modifier = Modifier.focusRequester(focusRequesters[i])
            )
            LaunchedEffect(Unit) {
                focusRequesters[0].requestFocus()
            }
        }
    }
}