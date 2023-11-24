package com.example.documentsearch.screens.profile.authenticationUser

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.prototypes.ProfilePrototype
import com.example.documentsearch.patterns.authentication.ResendVerificationCode
import com.example.documentsearch.patterns.authentication.VerificationCodeInput
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Форма кода для регистрации пользователя
 * @param navController Контроллер навигации
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VerificationRegistration(navController: NavHostController, registrationData: ProfilePrototype, onProfileChange: (ProfilePrototype) -> Unit) {
    var codeVerify by remember { mutableStateOf("") } // Код для верификации регистрации
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)

    // TODO(Сделать отправку кода на почту)
    if (codeVerify == "0000") {
        codeVerify = ""
        CoroutineScope(Dispatchers.Main).launch {
            val signInProfile: ProfilePrototype? = ProfileRequestServicesImpl().addProfile(
                profile = registrationData
            )

            if (signInProfile != null) {
                preferencesManager.saveData(emailKeyPreferences, registrationData.email)
                preferencesManager.saveData(passwordKeyPreferences, registrationData.password)
                onProfileChange(signInProfile)
            }
        }
    }


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
                        text = "Подтверждение",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = TextColor,
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 30.dp)
                    )
                    // Поле с вводом кода
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        VerificationCodeInput(onCodeEntered = { codeVerify = it })
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