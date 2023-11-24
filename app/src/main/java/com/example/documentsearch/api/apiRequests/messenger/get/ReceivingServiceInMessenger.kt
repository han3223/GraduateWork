package com.example.documentsearch.api.apiRequests.messenger.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ReceivingServiceInMessenger : ClientAPI() {
    fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        var resultMessengers: MutableList<GetMessengerPrototypeDataBase>? = mutableListOf()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = messengerService.getAllMessengersUsingUserId(userId)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultMessengers = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultMessengers
    }
}