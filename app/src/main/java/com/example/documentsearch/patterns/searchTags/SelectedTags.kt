package com.example.documentsearch.patterns.searchTags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.dataClasses.Tag
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor

/**
 * Функция отображает выбранные теги из списка тегов в фильтре
 * @param selectedTags Список выбранных тегов
 * @param onSelectedTagChanged Обработчик изменения списка выбранных тегов
 */
@Composable
fun SelectedTags(selectedTags: SnapshotStateList<Tag>, onSelectedTagChanged: (Tag) -> Unit) {
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

    if (selectedTags.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 10.dp)
                .clip(RoundedCornerShape(18.dp))
                .height(35.dp)
                .background(AdditionalMainColorDark),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .shadow(
                        40.dp,
                        RoundedCornerShape(14.dp),
                        ambientColor = Color.White,
                        spotColor = Color.White
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(14.dp))
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectedTags.forEach {
                    Box(
                        modifier = Modifier
                            .background(color = MainColorDark, shape = RoundedCornerShape(size = 14.dp))
                            .clickable { onSelectedTagChanged(it) }
                            .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                    ) {
                        Text(
                            text = it.title,
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
                    Spacer(modifier = Modifier.width(5.dp))
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