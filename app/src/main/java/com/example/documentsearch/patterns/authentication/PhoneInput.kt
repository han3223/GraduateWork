package com.example.documentsearch.patterns.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.patterns.PhoneVisualTransformation
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText

class PhoneInput(
    mainBoxModifier: Modifier,
    textFieldModifier: Modifier,
    keyboardActions: KeyboardActions,
    label: String,
    styleLabel: TextStyle = HIGHLIGHTING_BOLD_TEXT,
    validColor: Color = Color.White,
    invalidList: List<ValidationText> = listOf(),
    activeTextField: Boolean = true,
    textStyle: TextStyle = ORDINARY_TEXT
) {
    private val mainBoxModifier: Modifier
    private val textFieldModifier: Modifier
    private val keyboardActions: KeyboardActions
    private val label: String
    private val styleLabel: TextStyle
    private val validColor: Color
    private val invalidList: List<ValidationText>
    private val activeTextField: Boolean
    private val textStyle: TextStyle

    init {
        this.mainBoxModifier = mainBoxModifier
        this.textFieldModifier = textFieldModifier
        this.keyboardActions = keyboardActions
        this.label = label
        this.styleLabel = styleLabel
        this.validColor = validColor
        this.invalidList = invalidList
        this.activeTextField = activeTextField
        this.textStyle = textStyle
    }

    @Composable
    fun Input(phoneNumber: String, onPhoneNumberChanged: (String) -> Unit) {
        val focused = remember { mutableStateOf(false) }

        Box(modifier = mainBoxModifier) {
            Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(text = label, style = styleLabel)

                PhoneTextField(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChanged = { onPhoneNumberChanged(it) },
                    onFocusChange = { focused.value = it },
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
                    Row(
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
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
                        Text(text = it.text, style = SECONDARY_TEXT)
                    }
                }
            }
        }
    }

    @Composable
    private fun PhoneTextField(
        phoneNumber: String,
        onPhoneNumberChanged: (String) -> Unit,
        onFocusChange: (Boolean) -> Unit,
        mask: String = "+0 (000) 000-00-00",
        maskNumber: Char = '0',
    ) {
        var active by remember { mutableStateOf(true) }

        BasicTextField(
            value = phoneNumber,
            onValueChange = { value -> onPhoneNumberChanged(value.take(mask.count { it == maskNumber })) },
            modifier = textFieldModifier.onFocusChanged {
                onFocusChange(it.isFocused)
                active = it.isFocused
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            visualTransformation = PhoneVisualTransformation(mask, maskNumber),
            textStyle = textStyle,
            singleLine = true,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(TextColor),
            decorationBox = { innerTextField ->
                if (phoneNumber.isEmpty()) {
                    Text(
                        text = "+7 (999) 999-99-99",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 2.dp)
                    ) { innerTextField() }

                    if (activeTextField || active) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(validColor))
                    }
                }
            }
        )
    }
}