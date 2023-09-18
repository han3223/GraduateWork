package com.example.documentsearch.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.documentsearch.screens.profile.authenticationUser.AnotherForgotPassword
import com.example.documentsearch.screens.profile.authenticationUser.ForgotCode
import com.example.documentsearch.screens.profile.authenticationUser.ForgotPassword
import com.example.documentsearch.screens.profile.authenticationUser.Login
import com.example.documentsearch.screens.profile.authenticationUser.NewPassword
import com.example.documentsearch.screens.profile.authenticationUser.Registration
import com.example.documentsearch.screens.profile.authenticationUser.VerificationRegistration


val profileScreen = mutableStateOf("login")
/**
 * Функция отображает блок профиля пользователя
 */
@Composable
fun ProfileScreen() {
    when(profileScreen.value) {
       "login" -> Login()
        "registration" -> Registration()
        "verify registration" -> VerificationRegistration()
        "forgot password" -> ForgotPassword()
        "another forgot password" -> AnotherForgotPassword()
        "forgot code" -> ForgotCode()
        "new password" -> NewPassword()
    }
}