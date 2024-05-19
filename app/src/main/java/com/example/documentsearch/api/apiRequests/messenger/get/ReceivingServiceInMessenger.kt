package com.example.documentsearch.api.apiRequests.messenger.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.ChatData
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInMessenger : ClientAPI() {
    suspend fun getMessengerByParticipants(participants: List<Long>): ChatData? {
        var resultMessenger: ChatData? = null

        try {
            val response = messengerService.getMessengerByParticipants(participants.toString())
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение мессенжера вернул пустое значение")
            else
                resultMessenger = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение мессенжера произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultMessenger
    }

    suspend fun getAllMessengersUsingUserId(userId: Long): List<ChatData> {
        var resultMessengers: List<ChatData> = listOf()

        try {
            val response = messengerService.getAllMessengersUsingUserId(userId = userId)
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение всех мессенжеров пользователя вернул пустое значение")
            else
                resultMessengers = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение всех мессенжеров пользователя произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultMessengers
    }
}