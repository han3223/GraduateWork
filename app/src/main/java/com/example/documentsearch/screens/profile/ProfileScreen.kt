package com.example.documentsearch.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.documentsearch.ui.theme.cacheProfileTags
import com.example.documentsearch.ui.theme.cacheUserProfile

class ProfileScreen: HeadersProfile(), Screen {
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
                MainInfoProfile(cacheUserProfile.value.getData()!!, navigator).Info(lazyListState = lazyListState)
            }
            item(1) {
                ProfileTags(tags = cacheProfileTags.value.getData()!!, cacheUserProfile.value.getData()!!).Tags()
            }
            item(2) {
                PersonalDocumentation().Documentation()
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
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
                    .clickable {
                        preferencesManager.removeData(emailKeyPreferences)
                        preferencesManager.removeData(passwordKeyPreferences)
                        cacheUserProfile.value.clearData()
                        navigator.replace(LoginScreen())
                    }
            )
        }
    }
}