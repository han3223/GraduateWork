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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangingPersonalNameScreen() : Screen, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var isValidPersonalName by remember { mutableStateOf(true) }
        val title = "Имя пользователя"
        val label = "Задать пользовательское имя"
        val helpText =
            "Вы можете задать публичное пользовательское имя. При помощи этого имени вас легко смогут найти пользователи и связаться с вами.\n\nПользовательское имя должно быть не менее 5 символов. Пользовательское имя может содержать символы a-z, 0-9 и подчёркивания."
        val placeholder = "Имя пользователя"
        var validationPersonalName by remember { mutableStateOf(true) }

        val basicChangingInfoScreen = BasicChangingInfoScreen(navigator)
        basicChangingInfoScreen.Screen(
            titleAttribute = title,
            onValueChange = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val personalName =
                        profileRequestServices.updatePersonalNameUsingEmail(
                            cacheUserProfile.value!!.email,
                            it
                        )
                    if (personalName != null) {
                        cacheUserProfile.value!!.personalName = it
                        navigator.pop()
                    }
                }
            },
            conditionValue = validationPersonalName,
            helpText = helpText,
            label = label,
            placeholder = placeholder,
            onIntermediateValueChange = {
                CoroutineScope(Dispatchers.Main).launch {
                    val profileRequestServices = ProfileRequestServicesImpl()
                    val profileByPersonalName =
                        profileRequestServices.getProfileUsingPersonalName(it)
                    isValidPersonalName =
                        !(profileByPersonalName != null && it.isNotEmpty() && profileByPersonalName != cacheUserProfile.value)
                    validationPersonalName =
                        Regex("^[a-zA-z0-9_]+$").matches(it) && isValidPersonalName
                }
            }
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChangingPersonalNameScreen> {
        override fun createFromParcel(parcel: Parcel): ChangingPersonalNameScreen {
            return ChangingPersonalNameScreen(parcel)
        }

        override fun newArray(size: Int): Array<ChangingPersonalNameScreen?> {
            return arrayOfNulls(size)
        }
    }
}