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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.ResendVerificationCode
import com.example.documentsearch.patterns.authentication.VerificationCodeInput
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor


/**
 * Форма кода для обновления пароля пользователя
 * @param navController Контроллер навигации
 */
@Composable
fun ForgotCode(navController: NavHostController, forgotCode: Int) {
    var codeForgotPassword by remember { mutableStateOf("") } // Код для восстановления пароля
    println("Это код который пришёл из базы $forgotCode")

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
                    // Поле с вводом кода
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        VerificationCodeInput(onCodeEntered = {
                            codeForgotPassword = it
                            if (it.toInt() == forgotCode)
                                navController.navigate(NavigationItem.NewPassword.route)
                        })
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            ResendVerificationCode(onResendClicked = {
                                /*TODO(Сделать повторную отправку кода пользователю)*/
                            })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}