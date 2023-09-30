package com.example.documentsearch.screens.profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.documentsearch.screens.profile.profileInfo.MainInfoProfile
import com.example.documentsearch.screens.profile.profileInfo.ProfileTags

/**
 * Функция отображает блок профиля пользователя
 */
@Composable
fun ProfileScreen(
    navController: NavHostController,
    personalName: String,
    personalInfo: String,
    numberPhone: String,
    email: String,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState
    ) {
        item(0) {
            MainInfoProfile(
                navController = navController,
                personalName = personalName,
                personalInfo = personalInfo,
                numberPhone = numberPhone,
                email = email,
                lazyListState = lazyListState
            )
        }
        item(1) {
            ProfileTags()
        }
        item(2) {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}