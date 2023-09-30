package com.example.documentsearch.patterns.searchTags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor

// TODO(Надо подумать над реализацией дизайна тегов)
/**
 * Функция отображает теги для фильтра прогруженные из базы данных
 */
@Composable
fun Tags(
    tags: List<String>,
    searchTagsValue: TextFieldValue,
    selectedTags: SnapshotStateList<String>,
    onSelectedTagChanged: (String) -> Unit
) {
    Text(
        text = "Теги",
        style = TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.montserrat_semi_bold)),
            fontWeight = FontWeight(600),
            color = TextColor,
        ),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp, top = 5.dp, bottom = 5.dp)
    )
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        tags.minus(selectedTags).filter { it.contains(searchTagsValue.text, true) }
            .forEach {
                Box(modifier = Modifier.padding(2.dp)) {
                    Box(
                        modifier = Modifier
                            .background(MainColorDark, RoundedCornerShape(14.dp))
                            .clickable {
                                onSelectedTagChanged(it)
                            }
                    ) {
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
                    }
                }
            }
    }
}