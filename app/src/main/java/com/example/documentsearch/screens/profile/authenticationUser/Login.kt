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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.ProfilesRequests
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Форма для входа пользователя в свой аккаунт
 * @param navController Контроллер для навигации
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(navController: NavHostController, onProfileChange: (Profile) -> Unit) {
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)

    var email by remember { mutableStateOf("") } // email
    var password by remember { mutableStateOf("") } // Пароль

    val keyboardController = LocalSoftwareKeyboardController.current
    val emailFocusRequester = remember { FocusRequester() } // Обработчик фокуса поля для email
    val passwordFocusRequester = remember { FocusRequester() } // Обработчик фокуса поля для пароля

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
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
                        text = "Вход",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 30.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        // Email
                        StandardInput(
                            value = email,
                            label = "Email:",
                            placeholder = "ivan.ivanov@gmail.com",
                            onValueChanged = { email = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    passwordFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(emailFocusRequester)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        // Пароль
                        StandardInput(
                            value = password,
                            label = "Пароль:",
                            placeholder = "*********",
                            onValueChanged = { password = it },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    passwordFocusRequester.freeFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(passwordFocusRequester)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val logInProfile: Profile? = ProfilesRequests().getProfile(email, password)

                                    if (logInProfile != null) {
                                        preferencesManager.saveData(emailKeyPreferences, email)
                                        preferencesManager.saveData(passwordKeyPreferences, password)
                                        onProfileChange(logInProfile)
                                        navController.navigate(NavigationItem.Profile.route)
                                    }
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
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                        }
                        Text(
                            text = "Зарегистрироваться",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontWeight = FontWeight(100),
                                color = TextColor,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .clickable { navController.navigate(NavigationItem.Registration.route) }
                        )
                        Text(
                            text = "Забыли пароль?",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontWeight = FontWeight(100),
                                color = TextColor,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 30.dp)
                                .clickable { navController.navigate(NavigationItem.ForgotPassword.route) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}