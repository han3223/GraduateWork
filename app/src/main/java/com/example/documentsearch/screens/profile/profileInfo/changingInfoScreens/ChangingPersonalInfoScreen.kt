package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.prototype.BasicChangingInfoScreen
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangingPersonalInfoScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val title = "О себе"
        val label = "Расскажите о себе"
        val helpText =
            "Здесь вы можете кратко написать о себе. Эта информация будет видна пользователям, которые зайдут в ваш профиль."
        val placeholder = "О себе"

        val basicChangingInfoScreen = BasicChangingInfoScreen(navigator)
        basicChangingInfoScreen.Screen(
            titleAttribute = title,
            onValueChange = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val personalInfo =
                        profileRequestServices.updatePersonalInfoUsingEmail(
                            cacheUserProfile.value.getData()!!.email,
                            it
                        )
                    if (personalInfo != null) {
                        cacheUserProfile.value.getData()!!.personalInfo = it
                        navigator.pop()
                    }
                }
            },
            conditionValue = true,
            helpText = helpText,
            label = label,
            placeholder = placeholder,
            onIntermediateValueChange = {},
            maxWords = 120,
            counter = true,
            singleLine = false
        )
    }
}