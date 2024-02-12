package com.example.documentsearch.api.apiRequests.messenger.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInMessenger : ClientAPI() {
    suspend fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        var resultMessengers: MutableList<GetMessengerPrototypeDataBase>? = mutableListOf()

        try {
            val response = messengerService.getAllMessengersUsingUserId(userId = userId)
            val json = requestHandling(response = response)
            if (json == null)
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