package com.example.documentsearch.header.addFriend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.example.documentsearch.patterns.HeaderPrototype


@Composable
fun HeaderProfileUser() {
    Box(
        modifier = Modifier
            .zIndex(3f)
            .fillMaxWidth()
    ) {
        HeaderPrototype(height = 120)
    }
}