package com.example.documentsearch.screens.profile.profileInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.patterns.SearchTag
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.MainColorLight


class ProfileTags(tags: List<TagPrototype>, userProfile: UserProfilePrototype) {
    private val tags: List<TagPrototype>
    private val profile: UserProfilePrototype

    init {
        this.tags = tags
        this.profile = userProfile
    }

    @Composable
    fun Tags() {
        var titleTag by remember { mutableStateOf("") } // Значение в поиске
        var selectedTags = remember { mutableStateListOf<TagPrototype>() }
        val searchTag = SearchTag()
        profile.tags?.map { selectedTags.add(tags.first { tag -> tag.id == it }) }

        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .heightIn(0.dp, 350.dp)
                .padding(top = 15.dp)
                .clip(shape = RoundedCornerShape(size = 33.dp))
                .background(color = MainColorLight)
                .padding(horizontal = 20.dp),
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