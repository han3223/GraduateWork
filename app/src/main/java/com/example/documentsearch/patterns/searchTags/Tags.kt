package com.example.documentsearch.patterns.searchTags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.dataClasses.Tag
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.TextColor

// TODO(Надо подумать над реализацией дизайна тегов)
/**
 * Функция отображает теги для фильтра прогруженные из базы данных
 * @param tags Список тегов
 * @param searchTagsValue Значение в текстовом поле поиска
 * @param selectedTags Список выбранных тегов
 * @param onSelectedTagChanged Обработчик изменения списка выбранных тегов
 */
@Composable
fun Tags(
    tags: List<Tag>,
    searchTagsValue: TextFieldValue,
    selectedTags: SnapshotStateList<Tag>,
    onSelectedTagChanged: (Tag) -> Unit
) {
    val sortedTags = tags.minus(selectedTags).filter { it.title.contains(searchTagsValue.text, true) }

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
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp)),
            verticalAlignment = Alignment.CenterVertically,
            state = rememberLazyListState()
        ) {
            itemsIndexed(sortedTags) { index: Int, item: Tag ->
                Spacer(modifier = Modifier.width(2.5.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = MainColorDark,
                            shape = RoundedCornerShape(size = 14.dp)
                        )
                        .clickable { onSelectedTagChanged(item) }
                        .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                ) {
                    Text(
                        text = item.title,
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
                Spacer(modifier = Modifier.width(2.5.dp))
            }
        }
    }
}