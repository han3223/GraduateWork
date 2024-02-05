package com.example.documentsearch.api.apiRequests.document.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Document.documentService
import com.example.documentsearch.prototypes.DocumentPrototype
import kotlinx.serialization.json.Json

class ReceivingServiceInDocument : ClientAPI() {
    suspend fun getAllDocuments(): List<DocumentPrototype> {
        var resultDocuments = listOf<DocumentPrototype>()
        try {
            val response = requestHandling(documentService.getAllDocuments())
            if (response != null)
                resultDocuments = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultDocuments
    }

    suspend fun getDocuments(
        title: String,
        category: String?,
        dateAfter: String?,
        dateBefore: String?,
        tags: String?
    ): List<DocumentPrototype> {
        var resultDocuments = listOf<DocumentPrototype>()
        try {
            val response = requestHandling(documentService.getDocuments(title, category, dateAfter, dateBefore, tags))
            if (response != null)
                resultDocuments = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultDocuments
    }
}