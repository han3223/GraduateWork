package com.example.documentsearch.header.documentScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


/**
 * Функция отображает header экрана
 */
@Composable
fun Header(tags: List<String>) {
    // Предназначен для инициализации header (обычный, активный фильтр, активная сортировка)
    var optionHeader by remember { mutableIntStateOf(R.drawable.header) }

    // Контейнер
    Box(modifier = Modifier.zIndex(3f)) {
        // Задний фон header
        Image(
            painter = painterResource(id = optionHeader),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Строка поиска документа
            SearchDocument()
            Row(
                modifier = Modifier
                    .padding(20.dp, 0.dp, 20.dp, 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Контейнер с сортировкой
                Box(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (optionHeader == R.drawable.header || optionHeader == R.drawable.active_filter)
                                optionHeader = R.drawable.active_sort
                            else if (optionHeader == R.drawable.active_sort)
                                optionHeader = R.drawable.header
                        },
                    )
                }) {
                    Sort()
                }
                // Контейнер с фильтром
                Box(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (optionHeader == R.drawable.header || optionHeader == R.drawable.active_sort)
                                optionHeader = R.drawable.active_filter
                            else if (optionHeader == R.drawable.active_filter)
                                optionHeader = R.drawable.header
                        },
                    )
                }) {
                    Filter()
                }
            }
        }
    }

    // Контейнер с активной сортировкой
    Box {
        AnimatedVisibility(
            visible = optionHeader == R.drawable.active_sort,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            SortActive()
        }
    }

    // Контейнер с активным фильтром
    Box {
        AnimatedVisibility(
            visible = optionHeader == R.drawable.active_filter,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            FilterActive(tags)
        }
    }
}