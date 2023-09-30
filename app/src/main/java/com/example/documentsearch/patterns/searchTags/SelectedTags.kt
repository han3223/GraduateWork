package com.example.documentsearch.patterns.searchTags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает выбранные теги из списка тегов в фильтре
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedTags(selectedTags: SnapshotStateList<String>, onSelectedTagChanged: (String) -> Unit) {
    if (selectedTags.isNotEmpty()) {
        Text(
            text = "Выбранные теги",
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                fontWeight = FontWeight(600),
                color = TextColor,
            ),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, bottom = 5.dp)
        )
    }
    // TODO(Разобраться с полоской прокрутки)

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        selectedTags.forEach {
            Box(modifier = Modifier.padding(2.dp)) {
                Box(
                    modifier = Modifier
                        .background(MainColorDark, RoundedCornerShape(14.dp))
                        .clickable {
                            onSelectedTagChanged(it)
                        }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
                                fontWeight = FontWeight(600),
                                color = TextColor,
                            ),
                            modifier = Modifier.padding(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            )
                        )
//                        Spacer(modifier = Modifier.width(5.dp))
//                        Spacer(modifier = Modifier.width(5.dp))
                    }

                }
            }
        }
    }

    if (selectedTags.isNotEmpty())
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(TextColor)
                .padding(top = 5.dp)
        )
}