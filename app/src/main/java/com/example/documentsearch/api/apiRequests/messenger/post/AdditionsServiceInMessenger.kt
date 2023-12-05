package com.example.documentsearch.api.apiRequests.messenger.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.AddMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessenger : ClientAPI() {
    suspend fun addMessenger(messenger: AddMessengerPrototypeDataBase): GetMessengerPrototypeDataBase? {
        val messengerJson: String = Json.encodeToString(messenger)
        val messengerInRequestBody: RequestBody = messengerJson.toRequestBody(requestMediaType)

        var resultMessengerFullInfo: GetMessengerPrototypeDataBase? = null
        try {
            val response = requestHandling(messengerService.addMessenger(messengerInRequestBody))
            if (response != null)
                resultMessengerFullInfo = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultMessengerFullInfo
    }
}