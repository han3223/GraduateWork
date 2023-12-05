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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_UNDERLINE_TEXT
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginScreen(navigationController: NavController) : HeadersProfile() {
    private val navigationController: NavController
    private lateinit var preferencesManager: PreferencesManager


    init {
        this.navigationController = navigationController
    }

    @Composable
    fun Screen(onProfileChange: (UserProfilePrototype?) -> Unit) {
        Box {
            super.BasicHeader()
            Body { onProfileChange(it) }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Body(onProfileChange: (UserProfilePrototype?) -> Unit) {
        val context = LocalContext.current
        preferencesManager = PreferencesManager(context)

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

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
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Вход",
                        style = MAXIMUM_TEXT,
                        modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Separator()
                        Email(email, emailFocusRequester, passwordFocusRequester) { email = it }

                        Separator()
                        Password(
                            password,
                            passwordFocusRequester,
                            keyboardController
                        ) { password = it }

                        Separator()
                        ButtonLogIn(email, password) { onProfileChange(it) }

                        NavigateSignIn()
                        NavigateForgotPassword()
                    }
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    @Composable
    private fun Email(
        email: String,
        currentRequester: FocusRequester,
        nextRequester: FocusRequester,
        onEmailChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Email:",
            placeholder = "ivan.ivanov@gmail.com",
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
        )

        standardInput.Input(value = email, onValueChanged = { onEmailChange(it) })
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun Password(
        password: String,
        currentRequester: FocusRequester,
        keyboardController: SoftwareKeyboardController?,
        onPasswordChange: (String) -> Unit
    ) {
        val standardInput = StandardInput(
            label = "Пароль:",
            placeholder = "*********",
            visualTransformation = PasswordVisualTransformation('*'),
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
        )

        standardInput.Input(value = password, onValueChanged = { onPasswordChange(it) })
    }

    @Composable
    private fun ButtonLogIn(
        email: String,
        password: String,
        onProfileChange: (UserProfilePrototype?) -> Unit
    ) {
        Button(
            onClick = {
                logIn(email, password) {
                    onProfileChange(it)
                    navigationController.navigate(NavigationItem.Profile.route)
                }
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
                text = "Войти",
                style = HEADING_TEXT,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }

    private fun logIn(
        email: String,
        password: String,
        onProfileChange: (UserProfilePrototype?) -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val profileRequestServices = ProfileRequestServicesImpl()
            val getProfile = profileRequestServices.getProfileUsingEmailAndPassword(email, password)

            if (getProfile != null) {
                preferencesManager.saveData(emailKeyPreferences, email)
                preferencesManager.saveData(passwordKeyPreferences, password)
                onProfileChange(getProfile)
            }
        }
    }

    @Composable
    private fun NavigateSignIn() {
        Text(
            text = "Зарегистрироваться",
            style = ORDINARY_TEXT,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable { navigationController.navigate(NavigationItem.Registration.route) }
        )
    }

    @Composable
    private fun NavigateForgotPassword() {
        Text(
            text = "Забыли пароль?",
            style = HIGHLIGHTING_UNDERLINE_TEXT,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 30.dp)
                .clickable { navigationController.navigate(NavigationItem.ForgotPassword.route) }
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