package com.example.documentsearch.screens.document

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.example.documentsearch.prototypes.TagPrototype
import java.time.LocalDate

class SearchSupport() {
    private val sort = Sort()
    private val filter = Filter()
    private var tags: List<TagPrototype> = listOf()

    constructor(tags: List<TagPrototype>) : this() {
        this.tags = tags
    }

    @Composable
    fun SupportInActive(onTapSortChange: (Unit) -> Unit, onTapFilterChange: (Unit) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            sort.InActive(onTapChange = { onTapSortChange(it) })
            filter.InActive(onTapChange = { onTapFilterChange(it) })
        }
    }

    @Composable
    fun SupportActive(
        dateFrom: LocalDate,
        dateBefore: LocalDate,
        category: String,
        selectedTags: List<TagPrototype>,
        isActiveSort: Boolean,
        isActiveFilter: Boolean,
        onDateFromChange: (LocalDate) -> Unit,
        onDateBeforeChange: (LocalDate) -> Unit,
        onCategoryChange: (String) -> Unit,
        onSelectedTagsChange: (List<TagPrototype>) -> Unit
    ) {
        AnimatedVisibility(
            visible = isActiveSort,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            sort.Active()
        }

        AnimatedVisibility(
            visible = isActiveFilter,
            enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
            modifier = Modifier.zIndex(1f)
        ) {
            filter.ActiveDocument(
                tags = tags,
                dateFrom = dateFrom,
                dateBefore = dateBefore,
                category = category,
                selectedTags = selectedTags,
                onDateFromChange = { onDateFromChange(it) },
                onDateBeforeChange = { onDateBeforeChange(it) },
                onCategoryChange = { onCategoryChange(it) },
                onSelectedTagsChange = { onSelectedTagsChange(it) })
        }
    }
}