package com.example.documentsearch.api.apiRequests.tag.get

import android.annotation.SuppressLint
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Tag.tagService
import kotlinx.serialization.json.Json

class ReceivingServiceInTag : ClientAPI() {
    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getDocumentTags(): List<com.example.documentsearch.prototypes.TagPrototype> {
        var resultDocumentTags: List<com.example.documentsearch.prototypes.TagPrototype> = listOf()
        try {
            val response = requestHandling(tagService.getDocumentTags())
            if (response != null)
                resultDocumentTags = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultDocumentTags
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getProfileTags(): List<com.example.documentsearch.prototypes.TagPrototype> {
        var resultProfileTags: List<com.example.documentsearch.prototypes.TagPrototype> = listOf()
        try {
            val response = requestHandling(tagService.getProfileTags())
            if (response != null)
                resultProfileTags = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultProfileTags
    }
}