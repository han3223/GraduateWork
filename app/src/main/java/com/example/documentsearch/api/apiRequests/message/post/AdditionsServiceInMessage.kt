package com.example.documentsearch.api.apiRequests.message.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessage: ClientAPI() {
    fun addMessage(message: MessagePrototype): Boolean {
        val messageJson: String = Json.encodeToString(message)
        val messageInRequestBody: RequestBody = messageJson.toRequestBody(requestMediaType)

        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = Message.messageServices.addMessage(messageInRequestBody)
                if (response.isSuccessful)
                    isSuccessful = true
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }
}