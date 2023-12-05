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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.example.documentsearch.validation.Validation

class ReplacePasswordScreen(navigationController: NavController) : HeadersProfile() {
    private val navigationController: NavController

    init {
        this.navigationController = navigationController
    }

    @Composable
    fun Screen(onNewPasswordChange: (Pair<String, String>) -> Unit) {
        var changedOldPassword by remember { mutableStateOf("") }
        var changedNewPassword by remember { mutableStateOf("") }
        var changedRepeatNewPassword by remember { mutableStateOf("") }

        Box {
            super.HeaderProfilePasswordChanged(
                navigationController = navigationController,
                newPasswordEnter = changedNewPassword,
                repeatPasswordEnter = changedRepeatNewPassword,
                changeValue = { onNewPasswordChange(Pair(changedOldPassword, changedNewPassword)) }
            )
            Body(
                oldPassword = changedOldPassword,
                onOldPasswordChanged = { changedOldPassword = it },
                newPassword = changedNewPassword,
                onNewPasswordChanged = { changedNewPassword = it },
                repeatNewPassword = changedRepeatNewPassword,
                onRepeatNewPasswordChanged = { changedRepeatNewPassword = it }
            )
        }
    }

    @Composable
    private fun Body(
        oldPassword: String,
        onOldPasswordChanged: (String) -> Unit,
        newPassword: String,
        onNewPasswordChanged: (String) -> Unit,
        repeatNewPassword: String,
        onRepeatNewPasswordChanged: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .zIndex(2f)
                .padding(0.dp, super.getHeightHeader() - 33.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .height(10000.dp)
                .background(color = MainColorLight),
        ) {
            OldPassword(oldPassword = oldPassword) { onOldPasswordChanged(it) }
            Separator()

            NewPassword(newPassword = newPassword) { onNewPasswordChanged(it) }
            Separator()

            RepeatNewPassword(repeatNewPassword = repeatNewPassword) { onRepeatNewPasswordChanged(it) }
            Separator()

            Text(
                text = "Здесь вы можете поменять свой старый пароль на новый. \n\nПароль должен содержать минимум один строчной символ, один прописной символ, одну цифру, один специальный символ, а также иметь минимум 8 символов.",
                style = ORDINARY_TEXT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp, 10.dp, 7.dp, 0.dp)
            )
        }
    }

    @Composable
    private fun OldPassword(oldPassword: String, onOldPasswordChange: (String) -> Unit) {
        val standardInput = StandardInput(
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 5.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Старый пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            textStyle = ORDINARY_TEXT,
        )

        standardInput.Input(value = oldPassword, onValueChanged = { onOldPasswordChange(it) })
    }

    @Composable
    private fun NewPassword(newPassword: String, onNewPasswordChanged: (String) -> Unit) {
        val standardInput = StandardInput(
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 0.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent),
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Новый пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            textStyle = ORDINARY_TEXT,
            validColor = if (Validation().isValidPassword(newPassword) || newPassword.isEmpty()) TextColor else Color.Red,
            isCheckValue = true
        )

        standardInput.Input(value = newPassword, onValueChanged = { onNewPasswordChanged(it) })
    }

    @Composable
    private fun RepeatNewPassword(
        repeatNewPassword: String,
        onRepeatNewPasswordChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .background(AdditionalMainColor)
                .padding(20.dp, 20.dp, 20.dp, 0.dp),
            textFieldModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = Color.Transparent),
            visualTransformation = PasswordVisualTransformation('*'),
            keyboardActions = KeyboardActions.Default,
            label = "Новый пароль",
            placeholder = "*********",
            activeTextField = false,
            styleLabel = HIGHLIGHTING_BOLD_TEXT,
            textStyle = ORDINARY_TEXT,
            validColor = if (Validation().isValidPassword(repeatNewPassword) || repeatNewPassword.isEmpty()) TextColor else Color.Red,
            isCheckValue = true
        )

        standardInput.Input(value = repeatNewPassword, onValueChanged = { onRepeatNewPasswordChange(it) })
    }

    @Composable
    private fun Separator() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainColor)
        )
    }
}