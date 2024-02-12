package com.example.documentsearch.api.apiRequests.document.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Document.documentService
import com.example.documentsearch.prototypes.DocumentPrototype
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInDocument : ClientAPI() {
    suspend fun getAllDocuments(): List<DocumentPrototype> {
        var resultDocuments = listOf<DocumentPrototype>()
        try {
            val response = documentService.getAllDocuments()
            val json = requestHandling(response = response)
            if (json == null)
                Log.i("Запрос", "Запрос на получение всех документов вернул пустое значение")
            else
                resultDocuments = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на вывод всех документов произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultDocuments
    }

    suspend fun getDocuments(
        title: String,
        category: String?,
        dateAfter: String?,
        dateBefore: String?,
        tags: List<String>?
    ): List<DocumentPrototype> {
        var resultDocuments = listOf<DocumentPrototype>()
        try {
            val response = documentService.getDocuments(
                title = title,
                category = category,
                dateAfter = dateAfter,
                dateBefore = dateBefore,
                tags = tags?.toString()
            )
            val json = requestHandling(response = response)
            if (json == null)
                Log.i("Запрос", "Запрос на получение документа вернул пустое значение")
            else
                resultDocuments = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на вывод документов произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultDocuments
    }
}