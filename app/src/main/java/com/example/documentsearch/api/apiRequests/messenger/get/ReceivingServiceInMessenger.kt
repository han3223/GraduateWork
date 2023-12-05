package com.example.documentsearch.api.apiRequests.messenger.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import kotlinx.serialization.json.Json

class ReceivingServiceInMessenger : ClientAPI() {
    suspend fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        var resultMessengers: MutableList<GetMessengerPrototypeDataBase>? = mutableListOf()
        try {
            val response = requestHandling(messengerService.getAllMessengersUsingUserId(userId))
            if (response != null)
                resultMessengers = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultMessengers
    }
}