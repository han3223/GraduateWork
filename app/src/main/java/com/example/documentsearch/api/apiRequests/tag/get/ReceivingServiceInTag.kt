package com.example.documentsearch.api.apiRequests.tag.get

import android.annotation.SuppressLint
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Tag.tagService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.json.Json

class ReceivingServiceInTag : ClientAPI() {
    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getDocumentTags(): List<com.example.documentsearch.prototypes.TagPrototype> {
        var response: String? = null
        var resultDocumentTags: List<com.example.documentsearch.prototypes.TagPrototype> = listOf()

        CoroutineScope(Dispatchers.Main).async {
            try {
                response = requestHandling(tagService.getDocumentTags())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }.await()

        if (response != null)
            resultDocumentTags = Json.decodeFromString(response!!)

        return resultDocumentTags
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getProfileTags(): List<com.example.documentsearch.prototypes.TagPrototype> {
        var response: String? = null
        var resultProfileTags: List<com.example.documentsearch.prototypes.TagPrototype> = listOf()

        CoroutineScope(Dispatchers.Main).async {
            try {
                response = requestHandling(tagService.getProfileTags())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }.await()

        if (response != null)
            resultProfileTags = Json.decodeFromString(response!!)

        return resultProfileTags
    }
}