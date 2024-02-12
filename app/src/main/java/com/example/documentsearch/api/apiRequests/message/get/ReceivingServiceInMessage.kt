package com.example.documentsearch.api.apiRequests.message.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Message.messageServices
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInMessage : ClientAPI() {
    suspend fun getMessagesFromMessenger(idMessenger: Long): MutableList<MessagePrototype> {
        var resultMessages: MutableList<MessagePrototype> = mutableListOf()

        try {
            val response = messageServices.getMessagesFromMessenger(idMessenger = idMessenger)
            val json = requestHandling(response = response)
            if (json == null)
                Log.i("Запрос", "Запрос на получение сообщений вернул пустое значение")
            else
                resultMessages = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение сообщений произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultMessages
    }
}