package com.example.documentsearch.screens.profile.authenticationUser

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.validation.Validation
import com.example.documentsearch.validation.ValidationText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class NewPasswordScreen(val email: String?, val numberPhone: String?) : HeadersProfile(), Screen, Parcelable {
    val validation = Validation()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    @Composable
    override fun Content() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Body() {
        var password by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val passwordFocusRequester = remember { FocusRequester() }
        val repeatPasswordFocusRequester = remember { FocusRequester() }

        val enabledButton = !validation.isMinLength(password) &&
                !validation.isWhitespace(password) &&
                !validation.isLowerCase(password) &&
                !validation.isUpperCase(password) &&
                !validation.isDigit(password) &&
                !validation.isSpecialCharacter(password) &&
                password == repeatPassword

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = rememberLazyListState()
        ) {
            item(0) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 33.dp))
                        .background(color = MainColorLight),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Восстановление пароля",
                            style = MAXIMUM_TEXT,
                            modifier = Modifier
                                .padding(20.dp, 20.dp, 20.dp, 30.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Separator()
                            Password(
                                password = password,
                                currentRequester = passwordFocusRequester,
                                nextRequester = repeatPasswordFocusRequester,
                            ) { password = it }

                            Separator()
                            RepeatPassword(
                                password = password,
                                repeatPassword = repeatPassword,
                                currentRequester = repeatPasswordFocusRequester,
                                keyboardController = keyboardController,
                            ) { repeatPassword = it }

                            Separator()
                            ButtonRepeatPassword(enabledButton, password)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    @Composable
    private fun Password(
        password: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onPasswordChange: (String) -> Unit
    ) {
        val passwordValidationText = listOf(
            ValidationText(validation.isMinLength(password), "Минимум 8 символов"),
            ValidationText(validation.isWhitespace(password), "Не допускаются пробелы"),
            ValidationText(validation.isLowerCase(password), "Минимум один строчной символ"),
            ValidationText(validation.isUpperCase(password), "Минимум один заглавный символ"),
            ValidationText(validation.isDigit(password), "Минимум одна цифра"),
            ValidationText(validation.isSpecialCharacter(password), "Минимум один специальный символ")
        )

        val standardInput = StandardInput(
            label = "Пароль:",
            placeholder = "*********",
            visualTransformation = PasswordVisualTransformation('*'),
            validColor = if (validation.isValidPassword(password) || password.isEmpty()) TextColor else Color.Red,
            invalidList = passwordValidationText,
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() }),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            isCheckValue = true
        )

        standardInput.Input(value = password, onValueChanged = { onPasswordChange(it) })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun RepeatPassword(
        password: String,
        repeatPassword: String,
        currentRequester: FocusRequester,
        keyboardController: SoftwareKeyboardController?,
        onRepeatPasswordChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Повторите пароль:",
            placeholder = "*********",
            visualTransformation = PasswordVisualTransformation('*'),
            validColor = if (password == repeatPassword || repeatPassword.isEmpty()) TextColor else Color.Red,
            invalidList = listOf(ValidationText(password != repeatPassword, "Пароли должны совпадать")),
            keyboardActions = KeyboardActions(
                onDone = {
                    currentRequester.freeFocus()
                    keyboardController?.hide()
                }
            ),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { },
            isCheckValue = true
        )

        standardInput.Input(value = repeatPassword, onValueChanged = { onRepeatPasswordChange(it) })
    }

    @Composable
    private fun ButtonRepeatPassword(enabled: Boolean, password: String) {
        val navigator = LocalNavigator.currentOrThrow
        Button(
            enabled = enabled,
            onClick = {
                replacePassword(password) { if (it) navigator.push(LoginScreen()) else ForgotPasswordScreen() }
                /*TODO(Сделать обработку входа пользователя после смены пароля)*/
            },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainColor,
                contentColor = TextColor
            )
        ) {
            Text(
                text = "Создать пароль",
                style = HEADING_TEXT,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }

    private fun replacePassword(password: String, onReplacePassword: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val profileRequestServices = ProfileRequestServicesImpl()
            var isUpdatePassword = false

            if (email != null)
                isUpdatePassword =
                    profileRequestServices.updatePasswordUsingEmail(email, password)
            else if (numberPhone != null)
                isUpdatePassword =
                    profileRequestServices.updatePasswordUsingPhoneNumber(numberPhone, password)

            onReplacePassword(isUpdatePassword)
        }
    }

    @Composable
    private fun Separator() {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AdditionalColor)
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(numberPhone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewPasswordScreen> {
        override fun createFromParcel(parcel: Parcel): NewPasswordScreen {
            return NewPasswordScreen(parcel)
        }

        override fun newArray(size: Int): Array<NewPasswordScreen?> {
            return arrayOfNulls(size)
        }
    }
}