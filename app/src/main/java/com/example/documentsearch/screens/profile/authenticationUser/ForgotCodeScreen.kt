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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.documentsearch.navbar.NavigationItem
import com.example.documentsearch.patterns.authentication.VerificationCodeInput
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColorLight

class ForgotCodeScreen(navigationController: NavController, forgotCode: Int) :
    HeadersProfile() {
    private val navigationController: NavController
    private val forgotCode: Int

    init {
        this.navigationController = navigationController
        this.forgotCode = forgotCode
    }

    @Composable
    fun Screen() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @Composable
    private fun Body() {
        println("Это код который пришёл из базы $forgotCode")

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
                        text = "Восстановление пароля",
                        style = MAXIMUM_TEXT,
                        modifier = Modifier
                            .padding(20.dp, 20.dp, 20.dp, 30.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    VerificationCode()
                    ResendVerificationCode()
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    @Composable
    private fun VerificationCode() {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            val verificationCodeInput = VerificationCodeInput()
            verificationCodeInput.Input {
                if (it.toInt() == forgotCode)
                    navigationController.navigate(NavigationItem.NewPassword.route)
            }
        }
    }

    @Composable
    private fun ResendVerificationCode() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                val verificationCodeInput = VerificationCodeInput()
                verificationCodeInput.ResendVerificationCode {
                    /*TODO(Сделать повторную отправку кода пользователю)*/
                }
            }
        }
    }
}
