package com.example.documentsearch.api.apiRequests.document.post

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Document.documentService
import com.example.documentsearch.prototypes.DocumentPrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInDocument: ClientAPI() {
    suspend fun addDocument(document: DocumentPrototype): DocumentPrototype? {
        var resultDocument: DocumentPrototype? = null

        val documentJson: String = Json.encodeToString<DocumentPrototype>(value = document)
        val documentInRequestBody: RequestBody = documentJson.toRequestBody(contentType = requestMediaType)
        try {
            val response = documentService.addDocument(jsonPrototypeDocument = documentInRequestBody)
            val json = requestHandling(response = response)
            if (json == Status.ERROR.status)
                Log.i("Запрос", "Запрос на добавление документа вернул пустое значение")
            else
                resultDocument = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление документа произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultDocument
    }
}