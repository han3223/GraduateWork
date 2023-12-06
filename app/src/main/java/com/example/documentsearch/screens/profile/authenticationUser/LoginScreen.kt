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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.HEADING_TEXT
import com.example.documentsearch.ui.theme.HIGHLIGHTING_UNDERLINE_TEXT
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginScreen : HeadersProfile(), Screen {
    private lateinit var preferencesManager: PreferencesManager

    private val mainStandardInputModifier = Modifier
        .fillMaxWidth()
        .padding(30.dp, 10.dp, 30.dp, 0.dp)
    private val textFieldModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .background(color = Color.Transparent)

    @Composable
    override fun Content() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @Composable
    fun Body() {
        val context = LocalContext.current
        preferencesManager = PreferencesManager(context)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = rememberLazyListState()
        ) {
            item(0) {
                LoginForm()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun LoginForm() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }

        val formModifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 75.dp)
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)

        Column(
            modifier = formModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Title()
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Separator()
                Email(email, emailFocusRequester, passwordFocusRequester) { email = it }

                Separator()
                Password(password, passwordFocusRequester, keyboardController) { password = it }

                Separator()
                ButtonLogIn(email, password)

                NavigateSignIn()
                NavigateForgotPassword()
            }
        }
    }

    @Composable
    private fun Title() {
        Text(
            text = "Вход",
            style = MAXIMUM_TEXT,
            modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
        )
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
            mainBoxModifier = mainStandardInputModifier,
            textFieldModifier = textFieldModifier.focusRequester(currentRequester),
            keyboardActions = KeyboardActions(onDone = { nextRequester.requestFocus() })
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
            mainBoxModifier = mainStandardInputModifier,
            textFieldModifier = textFieldModifier.focusRequester(currentRequester),
            keyboardActions = KeyboardActions(
                onDone = {
                    currentRequester.freeFocus()
                    keyboardController?.hide()
                }
            )
        )

        standardInput.Input(value = password, onValueChanged = { onPasswordChange(it) })
    }

    @Composable
    private fun ButtonLogIn(email: String, password: String) {
        val navigator = LocalNavigator.currentOrThrow

        val modifierButton = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(10.dp))
        val colorButton = ButtonDefaults.buttonColors(
            backgroundColor = MainColor,
            contentColor = TextColor
        )

        Button(
            modifier = modifierButton,
            colors = colorButton,
            onClick = {
                logIn(email, password) {
                    cacheUserProfile.value.loadData(it)
                    navigator.replace(ProfileScreen())
                }
            }
        ) {
            Text(
                text = "Войти",
                style = HEADING_TEXT,
                textAlign = TextAlign.Center
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
        val navigator = LocalNavigator.currentOrThrow

        val modifierText = Modifier
            .padding(top = 20.dp)
            .clickable { navigator.push(RegistrationScreen()) }

        Text(
            text = "Зарегистрироваться",
            style = ORDINARY_TEXT,
            modifier = modifierText
        )
    }

    @Composable
    private fun NavigateForgotPassword() {
        val navigator = LocalNavigator.currentOrThrow

        val modifierText = Modifier
            .padding(top = 10.dp, bottom = 30.dp)
            .clickable { navigator.push(ForgotPasswordScreen()) }

        Text(
            text = "Забыли пароль?",
            style = HIGHLIGHTING_UNDERLINE_TEXT,
            modifier = modifierText
        )
    }

    @Composable
    private fun Separator() {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AdditionalColor))
    }
}