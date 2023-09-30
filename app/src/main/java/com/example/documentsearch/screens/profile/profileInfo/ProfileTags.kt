package com.example.documentsearch.screens.profile.profileInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.patterns.searchTags.SearchTags
import com.example.documentsearch.patterns.searchTags.SelectedTags
import com.example.documentsearch.patterns.searchTags.Tags
import com.example.documentsearch.ui.theme.MainColorLight

@Composable
fun ProfileTags() {
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

    var searchTagsValue by remember { mutableStateOf(TextFieldValue("")) }
    val selectedTags = remember { mutableStateListOf<String>() }

    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp)
            .clip(shape = RoundedCornerShape(size = 33.dp))
            .background(color = MainColorLight)
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.padding(vertical = 10.dp)) {
                    SearchTags(searchTagsValue = searchTagsValue, onSearchTagValueChange = { searchTagsValue = it })
                }
                SelectedTags(selectedTags = selectedTags, onSelectedTagChanged = { selectedTags.remove(it) })
                Tags(tags = tags, searchTagsValue = searchTagsValue, selectedTags = selectedTags, onSelectedTagChanged = { selectedTags.add(it) })
            }
        }
    }
}