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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.PhoneInput
import com.example.documentsearch.patterns.authentication.StandardInput
import com.example.documentsearch.ui.theme.AdditionalColor
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Форма восстановления пароля по номеру телефона
 * @param navController Контроллер навигации
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPassword(navController: NavHostController, onForgotCodeChange: (Int) -> Unit, onPhoneNumberChange: (String) -> Unit) {
    var lastName by remember { mutableStateOf("") } // Полное имя пользователя
    var numberPhone by remember { mutableStateOf("") } // Номер телефона пользователя

    val keyboardController = LocalSoftwareKeyboardController.current // Контроллер клавиатуры
    val fullNameFocusRequester = remember { FocusRequester() } // Обработчик фокуса для полного имени пользователя
    val numberPhoneFocusRequester = remember { FocusRequester() } // Обработчик фокуса для номера телефона пользователя

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
                    horizontalAlignment = CenterHorizontally,
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
                            .align(CenterHorizontally)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        // ФИО
                        StandardInput(
                            value = lastName,
                            label = "Фамилия:",
                            placeholder = "Иванов",
                            onValueChanged = { lastName = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    numberPhoneFocusRequester.requestFocus()
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
                        // Номер телефона
                        PhoneInput(
                            phoneNumber = numberPhone,
                            label = "Номер телефона",
                            onPhoneNumberChanged = { numberPhone = it },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    numberPhoneFocusRequester.freeFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            mainBoxModifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp, 10.dp, 30.dp, 0.dp),
                            textFieldModifier = Modifier
                                .focusRequester(numberPhoneFocusRequester)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(color = Color.Transparent)
                                .onFocusChanged { }
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(AdditionalColor))
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val forgotCode: Int? = ProfileRequestServicesImpl().getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName, numberPhone)

                                    if (forgotCode != null) {
                                        onPhoneNumberChange(numberPhone)
                                        onForgotCodeChange(forgotCode)
                                    }
                                    navController.navigate(NavigationItem.ForgotCode.route)
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
                            text = "Другой способ",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontWeight = FontWeight(100),
                                color = TextColor,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 30.dp)
                                .clickable { navController.navigate(NavigationItem.AnotherForgotPassword.route) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}