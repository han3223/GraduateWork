package com.example.documentsearch.patterns

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColorLight
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor

class SearchTag {
    @Composable
    fun BasicContainer(
        titleTag: String,
        onTitleChange: (String) -> Unit,
        selectedTags: SnapshotStateList<TagPrototype>,
        onSelectedTagsChanged: (SnapshotStateList<TagPrototype>) -> Unit,
        tags: List<TagPrototype>
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Выбранные теги:",
                    style = HIGHLIGHTING_BOLD_TEXT,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, bottom = 5.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                        .background(AdditionalMainColorDark, RoundedCornerShape(13.dp))
                        .padding(10.dp)
                ) {
                    SelectedTags(selectedTags = selectedTags) {
                        selectedTags.remove(it)
                        onSelectedTagsChanged(selectedTags)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Поиск тегов:",
                    style = HIGHLIGHTING_BOLD_TEXT,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, bottom = 5.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                        .background(AdditionalMainColorDark, RoundedCornerShape(13.dp))
                        .padding(10.dp)
                ) {
                    SearchTags(titleTag = titleTag) { onTitleChange(it) }
                    Tags(titleTag = titleTag, userSelectedTags = selectedTags, allTags = tags) {
                        selectedTags.add(it)
                        onSelectedTagsChanged(selectedTags)
                    }
                }
            }
        }

    }

    @Composable
    fun ProfileContainer(
        titleTag: String,
        onTitleChange: (String) -> Unit,
        selectedTags: SnapshotStateList<TagPrototype>,
        onSelectedTagsChanged: (SnapshotStateList<TagPrototype>) -> Unit,
        tags: List<TagPrototype>
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(250.dp)
                    .background(MainColorLight, RoundedCornerShape(18.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = "Выбранные теги:",
                    style = HIGHLIGHTING_BOLD_TEXT,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, bottom = 5.dp)
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(TextColor))
                SelectedTags(selectedTags = selectedTags) {
                    selectedTags.remove(it)
                    onSelectedTagsChanged(selectedTags)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(250.dp)
                    .background(MainColorLight, RoundedCornerShape(18.dp))
                    .padding(10.dp)
            ) {
                SearchTags(titleTag = titleTag) { onTitleChange(it) }
                Tags(titleTag = titleTag, userSelectedTags = selectedTags, allTags = tags) {
                    selectedTags.add(it)
                    onSelectedTagsChanged(selectedTags)
                }
            }
        }
    }

    @Composable
    private fun SelectedTags(
        selectedTags: SnapshotStateList<TagPrototype>,
        onSelectedTagChanged: (TagPrototype) -> Unit
    ) {
        if (selectedTags.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 5.dp, 0.dp, 5.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    state = rememberLazyListState()
                ) {
                    items(items = selectedTags) { tag: TagPrototype ->
                        Box(
                            modifier = Modifier
                                .clickable { onSelectedTagChanged(tag) }
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(size = 14.dp)
                                )
                        ) {
                            Text(
                                text = tag.title,
                                style = ORDINARY_TEXT.merge(TextStyle(textAlign = TextAlign.Center)),
                                modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(2.5.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun SearchTags(titleTag: String, onTitleChange: (String) -> Unit) {
        var isFocused by remember { mutableStateOf(false) }
        val textFieldModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp)
            .height(40.dp)
            .border(1.dp, TextColor, RoundedCornerShape(10.dp))
            .background(color = Color.Transparent)
            .onFocusChanged { isFocused = it.isFocused }

        BasicTextField(
            value = titleTag,
            onValueChange = { onTitleChange(it) },
            modifier = textFieldModifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = ORDINARY_TEXT,
            cursorBrush = SolidColor(TextColor),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 1.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextColor,
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)
                    )
                    if (titleTag.isEmpty() && !isFocused) {
                        Text(
                            text = "Поиск тегов",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }

    @Composable
    private fun Tags(
        titleTag: String,
        userSelectedTags: SnapshotStateList<TagPrototype>,
        allTags: List<TagPrototype>,
        onSelectedTagChanged: (TagPrototype) -> Unit
    ) {
        val sortedTags =
            allTags.minus(userSelectedTags).filter { it.title.contains(titleTag, true) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 5.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                state = rememberLazyListState()
            ) {
                items(items = sortedTags) { tag: TagPrototype ->
                    Spacer(modifier = Modifier.width(2.5.dp))
                    Box(modifier = Modifier.clickable { onSelectedTagChanged(tag) }) {
                        Text(
                            text = tag.title,
                            style = ORDINARY_TEXT.merge(TextStyle(textAlign = TextAlign.Center)),
                            modifier = Modifier.padding(
                                vertical = 5.dp,
                                horizontal = 5.dp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(2.5.dp))
                }
            }
        }
    }
}