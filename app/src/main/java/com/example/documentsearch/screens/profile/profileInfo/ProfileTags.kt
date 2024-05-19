package com.example.documentsearch.screens.profile.profileInfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.patterns.SearchTag
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileTags(tags: List<TagPrototype>, userProfile: UserProfilePrototype) {
    private val tags: List<TagPrototype>
    private val profile: UserProfilePrototype

    init {
        this.tags = tags
        this.profile = userProfile
    }

    @Composable
    fun Tags() {
        var searchValueTag by remember { mutableStateOf("") }
        var selectedTags = remember { mutableStateListOf<TagPrototype>() }
        val searchTag = SearchTag()

        profile.tags
            ?.replace("[", "")
            ?.replace("]", "")
            ?.split(",")?.map { it.trim() }
            ?.map { selectedTags.add(tags.first { tag -> tag.title == it }) }

        Box(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .heightIn(0.dp, 800.dp)
                .padding(top = 5.dp)
                .padding(horizontal = 0.dp),
        ) {
            searchTag.ProfileContainer(
                titleTag = searchValueTag,
                onTitleChange = { searchValueTag = it },
                selectedTags = selectedTags,
                onSelectedTagsChanged = {
                    selectedTags = it
                    CoroutineScope(Dispatchers.Main).launch {
                        val profileRequestServices = ProfileRequestServicesImpl()
                        profileRequestServices.updateTagsUsingEmail(
                            email = profile.email,
                            tags = it.map { tag -> tag.title }.toString()
                        )
                    }
                },
                tags = tags
            )
        }
    }
}