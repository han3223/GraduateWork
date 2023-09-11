package com.example.documentsearch.header.documentScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.header.documentScreen.filter.Filter
import com.example.documentsearch.header.documentScreen.filter.FilterActive
import com.example.documentsearch.header.documentScreen.sort.Sort
import com.example.documentsearch.header.documentScreen.sort.SortActive


val icon = mutableIntStateOf(R.drawable.header)
/**
 * Функция отображает header страницы
 */
@Composable
fun Header() {
    Box(modifier = Modifier.zIndex(3f)) {
        Image(
            painter = painterResource(id = icon.intValue),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            SearchDocument()
            Row(
                modifier = Modifier
                    .padding(20.dp, 0.dp, 20.dp, 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (icon.intValue == R.drawable.header || icon.intValue == R.drawable.active_filter)
                                icon.intValue = R.drawable.active_sort
                            else if (icon.intValue == R.drawable.active_sort)
                                icon.intValue = R.drawable.header
                        },
                    )
                }) {
                    Sort()
                }
                Box(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (icon.intValue == R.drawable.header || icon.intValue == R.drawable.active_sort)
                                icon.intValue = R.drawable.active_filter
                            else if (icon.intValue == R.drawable.active_filter)
                                icon.intValue = R.drawable.header
                        },
                    )
                }) {
                    Filter()
                }

            }
        }
    }
    Box {
        SortActive()
    }
    Box {
        FilterActive()
    }
}