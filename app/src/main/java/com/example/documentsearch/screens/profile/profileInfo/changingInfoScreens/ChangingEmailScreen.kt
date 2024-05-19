package com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.screens.profile.profileInfo.changingInfoScreens.prototype.BasicChangingInfoScreen
import com.example.documentsearch.ui.theme.cacheUserProfile
import com.example.documentsearch.validation.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangingEmailScreen() : Screen, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var isValidEmail by remember { mutableStateOf(true) }
        val title = "Email"
        val label = "Напишите свой email"
        val helpText = "Здесь вы можете сменить свой адрес электронной почты."
        val placeholder = "ivan.ivanov@gmail.com"

        val validation = Validation()
        var validationEmail by remember { mutableStateOf(true) }

        val basicChangingInfoScreen = BasicChangingInfoScreen(navigator)
        basicChangingInfoScreen.Screen(
            titleAttribute = title,
            onValueChange = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val email =
                        profileRequestServices.updateEmailUsingOldEmail(
                            cacheUserProfile.value!!.email,
                            it
                        )
                    if (email != null) {
                        cacheUserProfile.value!!.email = it
                        navigator.pop()
                    }
                }
            },
            conditionValue = validationEmail,
            helpText = helpText,
            label = label,
            placeholder = placeholder,
            onIntermediateValueChange = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val profileByEmail = profileRequestServices.getProfileUsingEmail(it)
                    isValidEmail =
                        !(profileByEmail != null && it.isNotEmpty() && profileByEmail != cacheUserProfile.value)
                    validationEmail = validation.isValidEmail(it) && isValidEmail
                }
            }
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChangingEmailScreen> {
        override fun createFromParcel(parcel: Parcel): ChangingEmailScreen {
            return ChangingEmailScreen(parcel)
        }

        override fun newArray(size: Int): Array<ChangingEmailScreen?> {
            return arrayOfNulls(size)
        }
    }

}