package com.example.documentsearch.api.apiRequests.document.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Document.documentService
import com.example.documentsearch.prototypes.AddDocumentPrototype
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInDocument: ClientAPI() {
    suspend fun addDocument(document: AddDocumentPrototype): String {
        val documentJson: String = Json.encodeToString(document)
        val documentInRequestBody: RequestBody = documentJson.toRequestBody(requestMediaType)
        return try {
            val response = requestHandling(documentService.addDocument(documentInRequestBody))
            response.toString()
        } catch (exception: Exception) {
            exception.message.toString()
        }
    }
}