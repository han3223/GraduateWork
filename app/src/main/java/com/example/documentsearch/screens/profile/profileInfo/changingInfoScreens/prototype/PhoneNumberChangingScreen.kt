package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.prototype

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
import cafe.adriel.voyager.navigator.Navigator
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalMainColor
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class PhoneNumberChangingScreen(val navigator: Navigator) : HeadersProfile() {

    @Composable
    fun Screen(onValueChange: (String) -> Unit) {
        var changedValue by remember { mutableStateOf("") }

        val title = "Номер телефона"
        var isValidNumberPhone by remember { mutableStateOf(true) }
        val validation = Validation()
        val validationNumberPhone =
            validation.isValidPhone(changedValue) && changedValue.length == 11 && isValidNumberPhone

        Box {
            super.HeaderProfileDataChanged(
                navigator = navigator,
                title = title,
                value = changedValue,
                onValueChange = { onValueChange(it) },
                conditionValidation = validationNumberPhone
            )
            Body(
                value = changedValue,
                valueChanged = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val profileRequestServices = ProfileRequestServicesImpl()
                        val profileByPhoneNumber = profileRequestServices.getProfileUsingPhoneNumber(it)
                        isValidNumberPhone = !(profileByPhoneNumber != null && it.isNotEmpty())
                    }

                    changedValue = it
                },
                conditionValue = validationNumberPhone
            )
        }
    }

    @Composable
    private fun Body(
        value: String,
        valueChanged: (String) -> Unit,
        conditionValue: Boolean = true,
        invalidList: List<ValidationText> = listOf()
    ) {
        val helpText = "Здесь вы можете ввести свой новый номер телефона. \nНомер телефона должен использовать международный формат."
        val label = "Напишите свой номер"

        Column(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .padding(0.dp, super.getHeightHeader() - 33.dp, 0.dp, 0.dp)
                .height(10000.dp)
                .background(color = MainColorLight),
        ) {
            val phoneInput = PhoneInput(
                mainBoxModifier = Modifier
                    .fillMaxWidth()
                    .background(AdditionalMainColor)
                    .padding(20.dp, 20.dp, 20.dp, 5.dp),
                textFieldModifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(color = Color.Transparent)
                    .onFocusChanged { },
                keyboardActions = KeyboardActions.Default,
                label = label,
                activeTextField = false,
                styleLabel = HIGHLIGHTING_BOLD_TEXT,
                textStyle = ORDINARY_TEXT,
                validColor = if (conditionValue) TextColor else Color.Red,
                invalidList = invalidList
            )

            phoneInput.Input(phoneNumber = value, onPhoneNumberChanged = { valueChanged(it) })
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