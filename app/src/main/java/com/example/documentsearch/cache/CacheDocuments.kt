package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.DocumentPrototype
import com.example.documentsearch.ui.theme.cacheDocuments

class CacheDocuments {
    fun getDocumentsFromCache(): List<DocumentPrototype>? {
        return cacheDocuments.value.getData()
    }

    fun loadDocuments(documents: List<DocumentPrototype>) {
        cacheDocuments.value.loadData(documents)
    }

    fun clearDocuments() {
        cacheDocuments.value.clearData()
    }
}