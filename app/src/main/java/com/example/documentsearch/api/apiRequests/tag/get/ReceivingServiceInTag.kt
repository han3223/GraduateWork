package com.example.documentsearch.api.apiRequests.tag.get

import android.annotation.SuppressLint
import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Tag.tagService
import com.example.documentsearch.prototypes.TagPrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInTag : ClientAPI() {
    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getDocumentTags(): List<TagPrototype> {
        var resultDocumentTags: List<TagPrototype> = listOf()
        try {
            val response = tagService.getDocumentTags()
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение тегов для документов вернул пустое значение")
            else
                resultDocumentTags = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение тегов для документов произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultDocumentTags
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getProfileTags(): List<TagPrototype> {
        var resultProfileTags: List<TagPrototype> = listOf()
        try {
            val response = tagService.getProfileTags()
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение тегов для пользователей вернул пустое значение")
            else
                resultProfileTags = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение тегов для пользователей произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultProfileTags
    }
}