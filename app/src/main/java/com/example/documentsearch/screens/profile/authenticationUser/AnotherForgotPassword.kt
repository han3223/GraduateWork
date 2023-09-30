package com.example.documentsearch.screens.profile.authenticationUser

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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor


/**
 * Форма восстановления пароля по почте
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnotherForgotPassword(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val fullNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
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
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center,
                            color = TextColor,
                        ),
                        modifier = Modifier
                            .padding(20.dp, 20.dp, 20.dp, 30.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        // ФИО
                        StandardInput(
                            value = fullName,
                            label = "ФИО:",
                            placeholder = "Иванов Иван Иванович",
                            onValueChanged = { fullName = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    emailFocusRequester.requestFocus()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(fullNameFocusRequester)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        // Email
                        StandardInput(
                            value = email,
                            label = "Email:",
                            placeholder = "ivan.ivanov@gmail.com",
                            onValueChanged = { email = it },
                            visualTransformation = PasswordVisualTransformation('*'),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    emailFocusRequester.freeFocus()
                                    keyboardController?.hide()
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
                        Button(
                            onClick = {
                                navController.navigate(NavigationItem.ForgotCode.route)
                                /*TODO(Сделать рассылку кода для пользователя)*/
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
                                text = "Получить код",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                    fontWeight = FontWeight(600),
                                    color = TextColor,
                                ),
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}