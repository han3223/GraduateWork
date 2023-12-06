package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.prototype.PhoneNumberChangingScreen
import com.example.documentsearch.ui.theme.cacheUserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangingNumberPhoneScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val phoneNumberChangingScreen = PhoneNumberChangingScreen(navigator)
        phoneNumberChangingScreen.Screen {
            CoroutineScope(Dispatchers.Main).launch {
                val profileRequestServices = ProfileRequestServicesImpl()
                val phoneNumber = profileRequestServices.updateNumberPhoneUsingEmail(
                    cacheUserProfile.value.getData()!!.email,
                    it
                )
                if (phoneNumber != null) {
                    cacheUserProfile.value.getData()!!.telephoneNumber = it
                    navigator.pop()
                }
            }
        }
    }

}