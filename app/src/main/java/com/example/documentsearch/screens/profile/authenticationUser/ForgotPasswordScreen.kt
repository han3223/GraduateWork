package com.example.documentsearch.screens.profile.authenticationUser

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_UNDERLINE_TEXT
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordScreen(navigationController: NavController) : HeadersProfile() {
    private val navigationController: NavController

    init {
        this.navigationController = navigationController
    }

    @Composable
    fun Screen(onNumberPhoneChange: (String) -> Unit, onForgotCodeChange: (Int) -> Unit) {
        Box {
            super.BasicHeader()
            Body(
                onNumberPhoneChange = { onNumberPhoneChange(it) },
                onForgotCodeChange = { onForgotCodeChange(it) }
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Body(onNumberPhoneChange: (String) -> Unit, onForgotCodeChange: (Int) -> Unit) {
        var lastName by remember { mutableStateOf("") }
        var numberPhone by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val lastNameFocusRequester = remember { FocusRequester() }
        val numberPhoneFocusRequester = remember { FocusRequester() }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = rememberLazyListState()
        ) {
            item(0) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .zIndex(2f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 33.dp))
                        .background(color = MainColorLight),
                    horizontalAlignment = CenterHorizontally,
                ) {
                    Text(
                        text = "Восстановление пароля",
                        style = MAXIMUM_TEXT,
                        modifier = Modifier
                            .padding(20.dp, 20.dp, 20.dp, 30.dp)
                            .align(CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Separator()
                        LastName(
                            lastName = lastName,
                            currentRequester = lastNameFocusRequester,
                            nextRequester = numberPhoneFocusRequester,
                        ) { lastName = it }

                        Separator()
                        NumberPhone(
                            numberPhone = numberPhone,
                            currentRequester = numberPhoneFocusRequester,
                            keyboardController = keyboardController,
                        ) { numberPhone = it }

                        Separator()
                        ButtonGetCode(
                            lastName,
                            numberPhone,
                            onNumberPhoneChange = { onNumberPhoneChange(it) },
                            onForgotCodeChange = { onForgotCodeChange(it) }
                        )
                        NavigateAnotherWay()
                    }
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    @Composable
    private fun LastName(
        lastName: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onLastChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Фамилия:",
            placeholder = "Иванов",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() }),
            mainBoxModifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            textFieldModifier = Modifier
                .focusRequester(currentRequester)
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Transparent)
                .onFocusChanged { }
        )

        standardInput.Input(value = lastName, onValueChanged = { onLastChange(it) })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun NumberPhone(
        numberPhone: String,
        currentRequester: FocusRequester,
        keyboardController: SoftwareKeyboardController?,
        onNumberPhoneChange: (String) -> Unit
    ) {
        val phoneInput = PhoneInput(
            label = "Номер телефона",
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
                .onFocusChanged { }
        )

        phoneInput.Input(phoneNumber = numberPhone, onPhoneNumberChanged = { onNumberPhoneChange(it) })
    }

    @Composable
    private fun ButtonGetCode(
        lastName: String,
        numberPhone: String,
        onNumberPhoneChange: (String) -> Unit,
        onForgotCodeChange: (Int) -> Unit
    ) {
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val forgotCode =
                        profileRequestServices.getProfileRecoveryCodeUsingLastNameAndPhoneNumber(
                            lastName,
                            numberPhone
                        )

                    if (forgotCode != null) {
                        onNumberPhoneChange(numberPhone)
                        onForgotCodeChange(forgotCode)
                    }
                    navigationController.navigate(NavigationItem.ForgotCode.route)
                }
                /*TODO(Сделать рассылку кода для пользователя)*/
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainColor,
                contentColor = TextColor
            )
        ) {
            Text(
                text = "Получить код",
                style = HEADING_TEXT,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }

    @Composable
    private fun NavigateAnotherWay() {
        Text(
            text = "Другой способ",
            style = HIGHLIGHTING_UNDERLINE_TEXT,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp)
                .clickable { navigationController.navigate(NavigationItem.AnotherForgotPassword.route) }
        )
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
}