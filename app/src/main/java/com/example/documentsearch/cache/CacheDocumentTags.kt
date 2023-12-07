package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.cacheDocumentTags

class CacheDocumentTags {
    fun getDocumentTagsFromCache(): List<TagPrototype>? {
        return cacheDocumentTags.value.getData()
    }

    fun loadDocumentTags(documentTags: List<TagPrototype>) {
        cacheDocumentTags.value.loadData(documentTags)
    }

    fun clearDocumentTags() {
        cacheDocumentTags.value.clearData()
    }
}