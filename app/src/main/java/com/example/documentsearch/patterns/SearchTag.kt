package com.example.documentsearch.patterns

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.AdditionalMainColorDark
import com.example.documentsearch.ui.theme.HIGHLIGHTING_BOLD_TEXT
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor

class SearchTag {
    @Composable
    fun Container(
        titleTag: String,
        onTitleChange: (String) -> Unit,
        selectedTags: SnapshotStateList<TagPrototype>,
        onSelectedTagsChanged: (SnapshotStateList<TagPrototype>) -> Unit,
        tags: List<TagPrototype>
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchTags(titleTag) { onTitleChange(it) }
            SelectedTags(selectedTags) {
                selectedTags.remove(it)
                onSelectedTagsChanged(selectedTags)
            }
            Tags(titleTag, selectedTags, tags) {
                selectedTags.add(it)
                onSelectedTagsChanged(selectedTags)
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
                    innerTextField()
                }
            }
        )
    }

    @Composable
    private fun SelectedTags(selectedTags: SnapshotStateList<TagPrototype>, onSelectedTagChanged: (TagPrototype) -> Unit) {
        if (selectedTags.isNotEmpty()) {
            Text(
                text = "Выбранные теги",
                style = HIGHLIGHTING_BOLD_TEXT,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, bottom = 5.dp)
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
                                .background(
                                    color = MainColorDark,
                                    shape = RoundedCornerShape(size = 14.dp)
                                )
                                .clickable { onSelectedTagChanged(it) }
                                .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                        ) {
                            Text(
                                text = it.title,
                                style = ORDINARY_TEXT,
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

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(TextColor)
                    .padding(top = 5.dp)
            )
        }
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

        Text(
            text = "Теги",
            style = HIGHLIGHTING_BOLD_TEXT,
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
                items(items = sortedTags) { tag: TagPrototype ->
                    Spacer(modifier = Modifier.width(2.5.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = MainColorDark,
                                shape = RoundedCornerShape(size = 14.dp)
                            )
                            .clickable { onSelectedTagChanged(tag) }
                            .border(1.dp, Color(0xCC354643), RoundedCornerShape(14.dp))
                    ) {
                        Text(
                            text = tag.title,
                            style = ORDINARY_TEXT,
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
}