package com.example.documentsearch.api.apiRequests.message.post

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Message.messageServices
import com.example.documentsearch.prototypes.MessagePrototype
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessage: ClientAPI() {
    suspend fun addMessage(message: MessagePrototype): MessagePrototype? {
        var resultMessage: MessagePrototype? = null

        val messageJson: String = Json.encodeToString(message)
        val messageInRequestBody: RequestBody = messageJson.toRequestBody(requestMediaType)
        try {
            val response = messageServices.addMessage(jsonPrototypeMessage = messageInRequestBody)
            val json = requestHandling(response = response)
            if (json == null)
                Log.i("Запрос", "Запрос на добавление сообщения вернул пустое значение")
            else
                resultMessage = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление сообщения произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultMessage
    }
}