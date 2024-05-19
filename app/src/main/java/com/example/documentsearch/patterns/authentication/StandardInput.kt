package com.example.documentsearch.patterns.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SECONDARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText

class StandardInput(
    mainBoxModifier: Modifier,
    textFieldModifier: Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String,
    styleLabel: TextStyle = HIGHLIGHTING_BOLD_TEXT,
    placeholder: String,
    validColor: Color = Color.White,
    invalidList: List<ValidationText> = listOf(),
    activeTextField: Boolean = true,
    textStyle: TextStyle = ORDINARY_TEXT,
    singleLine: Boolean = true,
    counter: Boolean = false,
    count: Int = 0,
    isCheckValue: Boolean = false
) {
    private val mainBoxModifier: Modifier
    private val textFieldModifier: Modifier
    private val keyboardOptions: KeyboardOptions
    private val keyboardActions: KeyboardActions
    private val visualTransformation: VisualTransformation
    private val label: String
    private val styleLabel: TextStyle
    private val placeholder: String
    private val validColor: Color
    private val invalidList: List<ValidationText>
    private val activeTextField: Boolean
    private val textStyle: TextStyle
    private val singleLine: Boolean
    private val counter: Boolean
    private val count: Int
    private val isCheckValue: Boolean

    init {
        this.mainBoxModifier = mainBoxModifier
        this.textFieldModifier = textFieldModifier
        this.keyboardOptions = keyboardOptions
        this.keyboardActions = keyboardActions
        this.visualTransformation = visualTransformation
        this.label = label
        this.styleLabel = styleLabel
        this.placeholder = placeholder
        this.validColor = validColor
        this.invalidList = invalidList
        this.activeTextField = activeTextField
        this.textStyle = textStyle
        this.singleLine = singleLine
        this.counter = counter
        this.count = count
        this.isCheckValue = isCheckValue
    }

    @Composable
    fun Input(value: String, onValueChanged: (String) -> Unit) {
        val focused = remember { mutableStateOf(false) }

        Column(modifier = mainBoxModifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = label,
                style = styleLabel
            )
            StandardTextField(
                value = value,
                onValueChanged = { onValueChanged(it) },
                onFocusChange = { focused.value = it },
            )
        }

        AnimatedVisibility(
            visible = focused.value,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)
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
                        Text(
                            text = it.text,
                            style = SECONDARY_TEXT
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun StandardTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChanged: (String) -> Unit,
        onFocusChange: (Boolean) -> Unit,
        alwaysDisable: Boolean = false
    ) {
        var isActiveNow by remember { mutableStateOf(true) }
        var visualTransform by remember { mutableStateOf(visualTransformation) }
        BasicTextField(
            value = if (value.length == count + 1 && counter) value.removeRange(value.length - 1, value.length) else value,
            onValueChange = { onValueChanged(it) },
            modifier = textFieldModifier.onFocusChanged {
                onFocusChange(it.isFocused)
                isActiveNow = it.isFocused
            },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransform,
            textStyle = textStyle,
            singleLine = singleLine,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(TextColor),
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Box(modifier = modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
                            innerTextField()
                        }
                        if (visualTransformation != VisualTransformation.None && value.isNotEmpty() && isCheckValue) {
                            var visibleValue by remember { mutableStateOf(false) }
                            androidx.compose.material.Icon(
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
                                style = SECONDARY_TEXT,
                                modifier = Modifier.width(30.dp)
                            )
                        }
                    }

                    if (activeTextField || isActiveNow && !alwaysDisable) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(validColor)
                        )
                    }
                }
            }
        )
    }
}