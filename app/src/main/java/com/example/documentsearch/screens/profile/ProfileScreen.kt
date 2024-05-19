package com.example.documentsearch.screens.profile

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.screens.profile.authenticationUser.LoginScreen
import com.example.documentsearch.screens.profile.profileInfo.MainInfoProfile
import com.example.documentsearch.screens.profile.profileInfo.PersonalDocumentation
import com.example.documentsearch.screens.profile.profileInfo.ProfileTags
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.cacheMessengers
import com.example.documentsearch.ui.theme.cacheProfileTags
import com.example.documentsearch.ui.theme.cacheUserProfile

class ProfileScreen() : HeadersProfile(), Screen, Parcelable {
    private lateinit var preferencesManager: PreferencesManager

    constructor(parcel: Parcel) : this()

    @Composable
    override fun Content() {
        Box {
            super.BasicHeader()
            Body()
        }
    }

    @Composable
    private fun Body() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        this.preferencesManager = PreferencesManager(context)
        val lazyListState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = lazyListState
        ) {
            item(0) {
                val userProfile = cacheUserProfile.value
                if (userProfile != null) {
                    val mainInfoProfile =
                        MainInfoProfile(userProfile, navigator)
                    mainInfoProfile.Info(lazyListState = lazyListState)
                }
            }
            item(1) {
                val userProfile = cacheUserProfile.value
                val profileTags = cacheProfileTags.value
                if (userProfile != null && profileTags != null) {
                    val userTags = ProfileTags(
                        tags = profileTags,
                        userProfile = userProfile
                    )
                    userTags.Tags()
                }
            }
            item(2) {
                val personalDocumentation = PersonalDocumentation()
                personalDocumentation.Documentation()
            }
            item(3) {
                Spacer(modifier = Modifier.height(10.dp))
                ExitProfile()
            }
            item(4) {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }

    @Composable
    private fun ExitProfile() {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .heightIn(0.dp, 350.dp)
                .clip(shape = RoundedCornerShape(size = 33.dp))
                .background(color = AdditionalMainColorDark)
        ) {
            Text(
                text = "Выйти из аккаунта",
                style = HIGHLIGHTING_BOLD_TEXT,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            navigator.replace(LoginScreen())

                            preferencesManager.removeData(emailKeyPreferences)
                            preferencesManager.removeData(passwordKeyPreferences)
                            cacheUserProfile.value = null
                            cacheMessengers.value = listOf()
                        })
                    }
            )
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileScreen> {
        override fun createFromParcel(parcel: Parcel): ProfileScreen {
            return ProfileScreen(parcel)
        }

        override fun newArray(size: Int): Array<ProfileScreen?> {
            return arrayOfNulls(size)
        }
    }
}