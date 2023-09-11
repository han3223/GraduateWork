package com.example.documentsearch.header.documentScreen.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.header.documentScreen.icon
import com.example.documentsearch.ui.theme.MainColorDark

/**
 * Кнопка фильтр (активная)
 */
@Composable
fun FilterActive() {
    AnimatedVisibility(
        visible = icon.intValue == R.drawable.active_filter,
        enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        modifier = Modifier
            .zIndex(1f)
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .heightIn(0.dp, 650.dp)
                .background(MainColorDark, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
        ) {
            Column(
                modifier = Modifier.padding(20.dp, 160.dp, 20.dp, 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Дата
                Dates()
                Categories()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.padding(vertical = 10.dp)) {
                            SearchTags()
                        }
                        SelectedTags()
                        Tags()
                    }
                }
            }
        }
    }
}