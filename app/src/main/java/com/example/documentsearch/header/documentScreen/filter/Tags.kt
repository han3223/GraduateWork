package com.example.documentsearch.header.documentScreen.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.R
import com.example.documentsearch.ui.theme.TextColor

var selectedTags = mutableStateListOf<String>()

// TODO(Надо подумать над реализацией дизайна тегов)
/**
 * Функция отображает теги для фильтра прогруженные из базы данных
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Tags() {
    val tags = listOf(
        "Исследования",
        "Новые открытия",
        "Наука",
        "Биология",
        "Физика",
        "Химия",
        "Медицина",
        "Генетика",
        "Астрономия",
        "Геология",
        "Археология",
        "Экология",
        "Психология",
        "История",
        "Технологии",
        "Антропология",
        "Социология",
        "Математика",
        "Электроника",
        "Кибернетика",
        "Робототехника",
        "Инженерия",
        "Геномика",
        "Нейробиология",
        "Квантовая физика",
        "Гравитационные волны",
        "Нанотехнологии",
        "Молекулярная биология",
        "Эволюционная биология",
        "Астрофизика",
        "Космология",
        "Энергетика",
        "Материаловедение",
        "Оптика",
        "Нефтегазовая индустрия",
        "Геофизика",
        "Термодинамика",
        "Ядерная физика",
        "Микробиология",
        "Наноматериалы",
        "Фармакология"
    )
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
    LazyColumn(
        modifier = Modifier
            .simpleVerticalScrollbar(rememberLazyListState())
            .padding(bottom = 3.dp)
    ) {
        item(0) {
            FlowRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                tags.minus(selectedTags).filter { it.contains(searchTagsValue.value.text, true) }
                    .forEach {
                        Box(modifier = Modifier.padding(2.dp)) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        selectedTags.add(it)
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
                                        horizontal = 5.dp
                                    )
                                )
                            }
                        }
                    }
            }
        }
    }
}