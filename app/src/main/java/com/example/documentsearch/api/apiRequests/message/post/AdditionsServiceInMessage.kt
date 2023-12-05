package com.example.documentsearch.api.apiRequests.message.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessage: ClientAPI() {
    suspend fun addMessage(message: MessagePrototype): Boolean {
        val messageJson: String = Json.encodeToString(message)
        val messageInRequestBody: RequestBody = messageJson.toRequestBody(requestMediaType)

        var isSuccessful = false
            try {
                val response = requestHandling(Message.messageServices.addMessage(messageInRequestBody))
                if (response != null)
                    isSuccessful = true
            } catch (exception: Exception) {
                println(exception.message)
            }

        return isSuccessful
    }
}