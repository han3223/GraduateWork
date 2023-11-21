package com.example.documentsearch.header.addFriend.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.dataClasses.Tag
import com.example.documentsearch.patterns.searchTags.SearchTags
import com.example.documentsearch.patterns.searchTags.SelectedTags
import com.example.documentsearch.patterns.searchTags.Tags
import com.example.documentsearch.ui.theme.AdditionalMainColorDark

@Composable
fun FilterActive(tags: List<Tag>) {
    var searchTagsValue by remember { mutableStateOf(TextFieldValue("")) }
    val selectedTags = remember { mutableStateListOf<Tag>() }

    // Контейнер для фильтра
    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .heightIn(0.dp, 650.dp)
            .background(AdditionalMainColorDark, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
    ) {
        Column(
            modifier = Modifier.padding(20.dp, 150.dp, 20.dp, 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                        SearchTags(
                            searchTagsValue = searchTagsValue,
                            onSearchTagValueChange = { searchTagsValue = it })
                    }
                    SelectedTags(
                        selectedTags = selectedTags,
                        onSelectedTagChanged = { selectedTags.remove(it) })
                    Tags(
                        tags = tags,
                        searchTagsValue = searchTagsValue,
                        selectedTags = selectedTags,
                        onSelectedTagChanged = { selectedTags.add(it) })
                }
            }
        }
    }
}