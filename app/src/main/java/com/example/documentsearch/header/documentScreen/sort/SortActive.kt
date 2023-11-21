package com.example.documentsearch.header.documentScreen.sort

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.SelectedColor
import com.example.documentsearch.ui.theme.TextColor


val activeSortElement = mutableStateOf(SortAZ)

/**
 * Функция отображает кнопку сортировки(активная)
 */
@Composable
fun SortActive() {
    val listSort = listOf(
        SortAZ,
        SortZA,
        SortDateNew,
        SortDateOld,
    )

    Box(
        modifier = Modifier
            .zIndex(1f)
            .width(255.dp)
            .background(AdditionalMainColorDark, RoundedCornerShape(0.dp, 0.dp, 20.dp, 0.dp))
    ) {
        Column(modifier = Modifier.padding(top = 150.dp, bottom = 15.dp)) {
            listSort.forEach { sortItem ->
                val sizeMultiplier = remember { Animatable(1f) }
                LaunchedEffect(sizeMultiplier) {
                    sizeMultiplier.animateTo(0.9f, animationSpec = spring())
                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 10.dp, 15.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    activeSortElement.value = sortItem
                                    sizeMultiplier.animateTo(0.95f, animationSpec = spring())
                                    awaitRelease()
                                    sizeMultiplier.animateTo(1f, animationSpec = spring())
                                },

                                )
                        }
                        .scale(sizeMultiplier.value),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = sortItem.icon),
                        contentDescription = sortItem.description,
                        modifier = Modifier
                            .size(24.dp),
                        tint = if (sortItem != activeSortElement.value) TextColor else SelectedColor
                    )
                    Text(
                        text = sortItem.title,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                            fontWeight = FontWeight(600),
                            color = if (sortItem != activeSortElement.value) TextColor else SelectedColor,
                        ),
                    )
                }
            }
        }
    }
}