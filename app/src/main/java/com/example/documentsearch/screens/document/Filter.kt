package com.example.documentsearch.screens.document

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.R
import com.example.documentsearch.patterns.SearchTag
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.MainColorDark
import com.example.documentsearch.ui.theme.ORDINARY_TEXT
import com.example.documentsearch.ui.theme.TextColor
import com.example.documentsearch.ui.theme.isClickBlock
import java.time.LocalDate

class Filter {
    @Composable
    fun InActive(onTapChange: (Unit) -> Unit) {
        Row(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (isClickBlock.value)
                        onTapChange(Unit)
                })
            },
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Фильтр",
                style = ORDINARY_TEXT,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(R.drawable.filter_white),
                contentDescription = "Фильтр",
                modifier = Modifier.size(24.dp),
                tint = TextColor,
            )
        }
    }

    @Composable
    fun ActiveDocument(
        tags: List<TagPrototype>,
        onDateFromChange: (LocalDate) -> Unit,
        onDateBeforeChange: (LocalDate) -> Unit,
        onCategoryChange: (String) -> Unit,
        onSelectedTagsChange: (List<TagPrototype>) -> Unit,
        dateFrom: LocalDate,
        dateBefore: LocalDate,
        category: String,
        selectedTags: List<TagPrototype>
    ) {
        var titleTag by remember { mutableStateOf("") }
        val dates = Dates()
        val categories = Categories()
        val searchTag = SearchTag()

        val mainContainerModifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .heightIn(0.dp, 650.dp)
            .background(MainColorDark, RoundedCornerShape(0.dp, 0.dp, 33.dp, 33.dp))
            .padding(20.dp, 150.dp, 20.dp, 20.dp)

        Column(
            modifier = mainContainerModifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            dates.ContainerWithDates(
                dateFrom = dateFrom,
                dateBefore = dateBefore,
                onDateFromChange = { onDateFromChange(it) },
                onDateBeforeChange = { onDateBeforeChange(it) }
            )
            categories.DropDownContainer(selectedCategory = category, onCategoryChange = { onCategoryChange(it) })
            searchTag.Container(
                titleTag = titleTag,
                onTitleChange = { titleTag = it },
                selectedTags = selectedTags.toMutableStateList(),
                onSelectedTagsChanged = {
                    onSelectedTagsChange(it.toList())
                },
                tags = tags
            )
        }
    }

    @Composable
    fun ActiveProfile(tags: List<TagPrototype>) {
        var titleTag by remember { mutableStateOf("") }
        var selectedTags = remember { mutableStateListOf<TagPrototype>() }
        val searchTag = SearchTag()

        val mainContainerModifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .heightIn(0.dp, 650.dp)
            .background(MainColorDark, RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
            .padding(20.dp, 150.dp, 20.dp, 20.dp)

        Column(
            modifier = mainContainerModifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            searchTag.Container(
                titleTag = titleTag,
                onTitleChange = { titleTag = it },
                selectedTags = selectedTags,
                onSelectedTagsChanged = { selectedTags = it },
                tags = tags
            )
        }
    }
}