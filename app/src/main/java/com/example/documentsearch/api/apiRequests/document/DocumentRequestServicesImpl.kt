package com.example.documentsearch.api.apiRequests.document

import com.example.documentsearch.listDocumet
import com.example.documentsearch.prototypes.DocumentWithPercentage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DocumentRequestServicesImpl {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    suspend fun getAllDocuments(): MutableList<DocumentWithPercentage> {
        return coroutine.async {
            listDocumet
        }.await()
    }
}