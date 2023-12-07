package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.cacheProfileTags

class CacheProfileTags {
    fun getProfileTagsFromCache(): List<TagPrototype>? {
        return cacheProfileTags.value.getData()
    }

    fun loadProfileTags(profileTags: List<TagPrototype>) {
        cacheProfileTags.value.loadData(profileTags)
    }

    fun clearProfileTags() {
        cacheProfileTags.value.clearData()
    }
}