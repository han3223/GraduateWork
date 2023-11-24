package com.example.documentsearch.api.apiRequests.message.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ReceivingServiceInMessage: ClientAPI() {
    fun getMessagesFromMessenger(idMessenger: Long): MutableList<MessagePrototype> {
        var resultMessages: MutableList<MessagePrototype> = mutableListOf()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = Message.messageServices.getMessagesFromMessenger(idMessenger)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultMessages = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultMessages
    }
}