package com.example.documentsearch.api.apiRequests.messenger.post

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.ChatData
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessenger : ClientAPI() {
    suspend fun addMessenger(messenger: ChatData): ChatData? {
        var resultMessenger: ChatData? = null

        val messengerJson: String = Json.encodeToString(value = messenger)

        val messengerInRequestBody: RequestBody = messengerJson.toRequestBody(requestMediaType)
        try {
            val response = messengerService.addMessenger(jsonPrototypeMessenger = messengerInRequestBody)
            val json = requestHandling(response = response)

            if (json == Status.ERROR.status)
                Log.i("Запрос", "Запрос на добавление месенжера вернул пустое значение")
            else
                resultMessenger = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление месенжера произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        Log.i("TEST", resultMessenger.toString())
        return resultMessenger
    }
}