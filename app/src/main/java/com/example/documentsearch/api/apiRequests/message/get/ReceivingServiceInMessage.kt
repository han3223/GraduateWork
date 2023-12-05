package com.example.documentsearch.api.apiRequests.message.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.serialization.json.Json

class ReceivingServiceInMessage : ClientAPI() {
    suspend fun getMessagesFromMessenger(idMessenger: Long): MutableList<MessagePrototype> {
        var resultMessages: MutableList<MessagePrototype> = mutableListOf()
        try {
            val response =
                requestHandling(Message.messageServices.getMessagesFromMessenger(idMessenger))
            if (response != null)
                resultMessages = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultMessages
    }
}