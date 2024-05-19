package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import android.os.Parcel
import android.os.Parcelable
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

class ChangingNumberPhoneScreen() : Screen, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val phoneNumberChangingScreen = PhoneNumberChangingScreen(navigator)
        phoneNumberChangingScreen.Screen {
            CoroutineScope(Dispatchers.Main).launch {
                val profileRequestServices = ProfileRequestServicesImpl()
                val phoneNumber = profileRequestServices.updateNumberPhoneUsingEmail(
                    cacheUserProfile.value!!.email,
                    it
                )
                if (phoneNumber != null) {
                    cacheUserProfile.value!!.phoneNumber = it
                    navigator.pop()
                }
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChangingNumberPhoneScreen> {
        override fun createFromParcel(parcel: Parcel): ChangingNumberPhoneScreen {
            return ChangingNumberPhoneScreen(parcel)
        }

        override fun newArray(size: Int): Array<ChangingNumberPhoneScreen?> {
            return arrayOfNulls(size)
        }
    }

}