package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.prototype.ReplacePasswordScreen
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangingPasswordScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val replacePasswordScreen = ReplacePasswordScreen(navigator)
        replacePasswordScreen.Screen {
            CoroutineScope(Dispatchers.Main).launch {
                val password: String? =
                    ProfileRequestServicesImpl().updatePasswordUsingEmail(
                        cacheUserProfile.value.getData()!!.email,
                        it.first,
                        it.second
                    )
                if (password != null) {
                    println("Пароль успешно обновлён")
                }
            }
        }
    }
}