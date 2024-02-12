package com.example.documentsearch.screens.document

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.SelectedColor
import com.example.documentsearch.ui.theme.SortAZ
import com.example.documentsearch.ui.theme.SortDateNew
import com.example.documentsearch.ui.theme.SortDateOld
import com.example.documentsearch.ui.theme.SortZA
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.isClickBlock

class Sort {
    private val activeSortElement = mutableStateOf(SortAZ)

    @Composable
    fun InActive(onTapChange: (Unit) -> Unit) {
        Row(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (isClickBlock.value)
                        onTapChange(Unit)
                })
            },
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(activeSortElement.value.icon),
                contentDescription = "Сортировать по",
                modifier = Modifier.size(24.dp),
                tint = TextColor,
            )
            Text(
                text = "Сортировать по",
                style = ORDINARY_TEXT,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }

    @Composable
    fun Active() {
        val listSort = listOf(SortAZ, SortZA, SortDateNew, SortDateOld)
        val columnModifier = Modifier
            .zIndex(1f)
            .width(255.dp)
            .background(MainColorDark, RoundedCornerShape(0.dp, 0.dp, 20.dp, 0.dp))
            .padding(top = 150.dp, bottom = 15.dp)

        Column(modifier = columnModifier) {
            listSort.forEach { sortItem ->
                val sizeMultiplier = remember { Animatable(1f) }
                LaunchedEffect(sizeMultiplier) {
                    sizeMultiplier.animateTo(0.9f, animationSpec = spring())
                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 10.dp, 15.dp)
                        .scale(sizeMultiplier.value)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    activeSortElement.value = sortItem
                                    sizeMultiplier.animateTo(0.95f, animationSpec = spring())
                                    awaitRelease()
                                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                                }
                            )
                        },
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = sortItem.icon),
                        contentDescription = sortItem.description,
                        modifier = Modifier.size(25.dp),
                        tint = if (sortItem != activeSortElement.value) TextColor else SelectedColor
                    )
                    Text(
                        text = sortItem.title,
                        style = ORDINARY_TEXT
                    )
                }
            }
        }
    }
}