package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.ValidationText

class BasicChangingInfoScreen(navigationController: NavController) :
    HeadersProfile() {
    private val navigationController: NavController

    init {
        this.navigationController = navigationController
    }

    @Composable
    fun Screen(
        titleAttribute: String,
        onValueChange: (String) -> Unit,
        conditionValue: Boolean,
        helpText: String,
        label: String,
        placeholder: String,
        onIntermediateValueChange: (String) -> Unit,
        maxWords: Int = 0,
        counter: Boolean = false,
        singleLine: Boolean = true
    ) {
        var changedValue by remember { mutableStateOf("") }

        Box {
            super.HeaderProfileDataChanged(
                navigationController = navigationController,
                title = titleAttribute,
                value = changedValue,
                onValueChange = { onValueChange(it) },
                conditionValidation = conditionValue
            )
            Body(
                value = changedValue,
                onValueChange = {
                    changedValue = it
                    onIntermediateValueChange(it)
                },
                helpText = helpText,
                label = label,
                placeholder = placeholder,
                conditionValue = conditionValue,
                maxWords = maxWords,
                counter = counter,
                singleLine = singleLine
            )
        }
    }

    @Composable
    private fun Body(
        value: String,
        onValueChange: (String) -> Unit,
        helpText: String,
        label: String,
        placeholder: String,
        conditionValue: Boolean,
        maxWords: Int = 0,
        counter: Boolean = false,
        singleLine: Boolean = true
    ) {
        Box(
            modifier = Modifier
                .zIndex(0f)
                .padding(0.dp, super.getHeightHeader() - 33.dp, 0.dp, 0.dp)
        ) {
            BasicChangingInfo(
                value = value,
                valueChanged = { onValueChange(it) },
                helpText = helpText,
                label = label,
                placeholder = placeholder,
                conditionValidation = conditionValue,
                maxWords = maxWords,
                counter = counter,
                singleLine = singleLine
            )
        }
    }

    @Composable
    private fun BasicChangingInfo(
        value: String,
        valueChanged: (String) -> Unit,
        helpText: String,
        label: String,
        placeholder: String,
        singleLine: Boolean = true,
        conditionValidation: Boolean = true,
        invalidList: List<ValidationText> = listOf(),
        counter: Boolean = false,
        maxWords: Int = 0
    ) {
        Column(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .height(10000.dp)
                .background(color = MainColorLight),
        ) {
            val standardInput = StandardInput(
                mainBoxModifier = Modifier
                    .fillMaxWidth()
                    .background(AdditionalMainColor)
                    .padding(20.dp, 20.dp, 20.dp, if (singleLine) 5.dp else 15.dp),
                textFieldModifier =
                if (singleLine)
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(color = Color.Transparent)
                        .onFocusChanged { }
                else
                    Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                keyboardActions = KeyboardActions.Default,
                label = label,
                placeholder = placeholder,
                activeTextField = false,
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                textStyle = ORDINARY_TEXT,
                singleLine = singleLine,
                validColor = if (conditionValidation) TextColor else Color.Red,
                invalidList = invalidList,
                counter = counter,
                count = maxWords
            )

            standardInput.Input(value = value, onValueChanged = { valueChanged(it) })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MainColor)
            )
            Text(
                text = helpText,
                style = ORDINARY_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp, 10.dp, 7.dp, 0.dp)
            )
        }
    }
}