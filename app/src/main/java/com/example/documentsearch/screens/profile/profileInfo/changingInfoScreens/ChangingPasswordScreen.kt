package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import android.os.Parcel
import android.os.Parcelable
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

class ChangingPasswordScreen() : Screen, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val replacePasswordScreen = ReplacePasswordScreen(navigator)
        replacePasswordScreen.Screen {
            CoroutineScope(Dispatchers.Main).launch {
                ProfileRequestServicesImpl().updatePasswordUsingEmail(
                    cacheUserProfile.value!!.email,
                    it.first,
                    it.second
                )
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChangingPasswordScreen> {
        override fun createFromParcel(parcel: Parcel): ChangingPasswordScreen {
            return ChangingPasswordScreen(parcel)
        }

        override fun newArray(size: Int): Array<ChangingPasswordScreen?> {
            return arrayOfNulls(size)
        }
    }
}