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
import androidx.navigation.NavController
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.screens.profile.profileInfo.MainInfoProfile
import com.example.documentsearch.screens.profile.profileInfo.PersonalDocumentation
import com.example.documentsearch.screens.profile.profileInfo.ProfileTags
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT

class ProfileScreen(
    navigationController: NavController,
    userProfile: UserProfilePrototype,
    tags: List<TagPrototype>
) : HeadersProfile() {
    private val navigationController: NavController
    private val profile: UserProfilePrototype
    private val tags: List<TagPrototype>

    private lateinit var preferencesManager: PreferencesManager

    init {
        this.navigationController = navigationController
        this.profile = userProfile
        this.tags = tags
    }

    @Composable
    fun Screen(onExitProfileChange: (Unit) -> Unit) {
        Box {
            super.BasicHeader()
            Body { onExitProfileChange(it) }
        }
    }

    @Composable
    private fun Body(onExitProfileChange: (Unit) -> Unit) {
        val context = LocalContext.current
        this.preferencesManager = PreferencesManager(context)
        val lazyListState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, super.getHeightHeader() - 33.dp, 5.dp, 0.dp),
            state = lazyListState
        ) {
            item(0) {
                MainInfoProfile(navigationController, profile).Info(lazyListState = lazyListState)
            }
            item(1) {
                ProfileTags(tags = tags, profile).Tags()
            }
            item(2) {
                PersonalDocumentation().Documentation()
            }
            item(3) {
                Spacer(modifier = Modifier.height(10.dp))
                ExitProfile { onExitProfileChange(it) }
            }
            item(4) {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }

    @Composable
    private fun ExitProfile(onExitProfileChange: (Unit) -> Unit) {
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
                        onExitProfileChange(Unit)
                    }
            )
        }
    }
}