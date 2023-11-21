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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.documentsearch.R
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.dataClasses.Tag
import com.example.documentsearch.preferences.PreferencesManager
import com.example.documentsearch.preferences.emailKeyPreferences
import com.example.documentsearch.preferences.passwordKeyPreferences
import com.example.documentsearch.screens.profile.profileInfo.MainInfoProfile
import com.example.documentsearch.screens.profile.profileInfo.PersonalDocumentation
import com.example.documentsearch.screens.profile.profileInfo.ProfileTags
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает блок профиля пользователя
 */
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profile: Profile,
    onExitProfileChange: (Boolean) -> Unit,
    tags: List<Tag>
) {
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState
    ) {
        // Основная информация о пользователе
        item(0) {
            MainInfoProfile(
                navController = navController,
                profile = profile,
                lazyListState = lazyListState
            )
        }
        // Теги пользователя
        item(1) {
            ProfileTags(tags = tags, profile)
        }
        // Документация пользователя
        item(2) {
            PersonalDocumentation()
        }
        // Выйти из аккаунта
        item(3) {
            Spacer(modifier = Modifier.height(10.dp))
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
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                        fontWeight = FontWeight(600),
                        color = TextColor,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(7.dp)
                        .fillMaxWidth()
                        .clickable {
                            preferencesManager.removeData(emailKeyPreferences)
                            preferencesManager.removeData(passwordKeyPreferences)
                            onExitProfileChange(true)
                        }
                )
            }
        }
        // Отступ от нижней границы
        item(4) {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}