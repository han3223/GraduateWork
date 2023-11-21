package com.example.documentsearch.header.addFriend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.dataClasses.Tag
import com.example.documentsearch.header.addFriend.filter.FilterActive
import com.example.documentsearch.header.documentScreen.filter.Filter
import com.example.documentsearch.patterns.HeaderPrototype
import com.example.documentsearch.ui.theme.MainColor
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.filter
import com.example.documentsearch.ui.theme.sort

@Composable
fun Header(tags: List<Tag>) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var isActiveSort by remember { mutableStateOf(false) }
    var isActiveFilter by remember { mutableStateOf(false) }

    // Контейнер
    Box(modifier = Modifier.zIndex(3f)) {
        // Задний фон header
        HeaderPrototype(
            height = 160,
            element = if (isActiveSort) sort else if (isActiveFilter) filter else "",
            modifier = if (isActiveSort) Modifier
                .align(Alignment.BottomStart)
                .height(40.dp)
                .width(255.dp)
            else if (isActiveFilter) Modifier
                .align(Alignment.BottomEnd)
                .height(40.dp)
                .width(screenWidthDp.dp - 33.dp)
            else Modifier,
            leftEllipseColor = if (isActiveSort) MainColorDark else MainColor,
            rightEllipseColor = if (isActiveFilter) MainColorDark else MainColor
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
            SearchUser()
            Row(
                modifier = Modifier
                    .padding(20.dp, 0.dp, 20.dp, 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Контейнер с фильтром
                Box(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (isActiveSort)
                                isActiveSort = false
                            else
                                isActiveFilter = !isActiveFilter
                        },
                    )
                }) {
                    Filter()
                }
            }
        }
    }

    // Контейнер с активным фильтром
    Box {
        AnimatedVisibility(
            visible = isActiveFilter,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            FilterActive(tags)
        }
    }
}