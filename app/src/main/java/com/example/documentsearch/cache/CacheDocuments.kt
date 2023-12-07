package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.DocumentWithPercentage
import com.example.documentsearch.ui.theme.cacheDocuments

class CacheDocuments {
    fun getDocumentsFromCache(): List<DocumentWithPercentage>? {
        return cacheDocuments.value.getData()
    }

    fun loadDocuments(documents: List<DocumentWithPercentage>) {
        cacheDocuments.value.loadData(documents)
    }

    fun clearDocuments() {
        cacheDocuments.value.clearData()
    }
}