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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.authentication.VerificationCodeInput
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.HeadersProfile
import com.example.documentsearch.screens.profile.ProfileScreen
import com.example.documentsearch.ui.theme.MAXIMUM_TEXT
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class VerificationRegistrationScreen(val registrationData: UserProfilePrototype) : HeadersProfile(), Screen {
    private lateinit var preferencesManager: PreferencesManager

    @Composable
    override fun Content() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @Composable
    private fun Body() {
        var codeVerify by remember { mutableStateOf("") }   
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        preferencesManager = PreferencesManager(context)

        // TODO(Сделать отправку кода на почту)
        if (codeVerify == "0000") {
            codeVerify = ""
            signIn { navigator.replace(ProfileScreen()) }
        }


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
                        text = "Подтверждение",
                        style = MAXIMUM_TEXT,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 30.dp)
                    )
                    VerificationCode { codeVerify = it }
                    ResendVerificationCode()
                }
                Spacer(modifier = Modifier.height(75.dp))
            }
        }
    }

    private fun signIn(onProfileChange: (UserProfilePrototype) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val profileRequestServices = ProfileRequestServicesImpl()
            val signInProfile = profileRequestServices.addProfile(registrationData)

            if (signInProfile != null) {
                preferencesManager.saveData(emailKeyPreferences, registrationData.email)
                preferencesManager.saveData(passwordKeyPreferences, registrationData.password)
                cacheUserProfile.value.loadData(signInProfile)
                onProfileChange(signInProfile)
            }
        }
    }

    @Composable
    private fun VerificationCode(onCodeVerify: (String) -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            val verificationCodeInput = VerificationCodeInput()
            verificationCodeInput.Input { onCodeVerify(it) }
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