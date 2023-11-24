package com.example.documentsearch.api.apiRequests.messenger.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Messenger.messengerService
import com.example.documentsearch.prototypes.AddMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInMessenger : ClientAPI() {
    fun addMessenger(messenger: AddMessengerPrototypeDataBase): GetMessengerPrototypeDataBase? {
        val messengerJson: String = Json.encodeToString(messenger)
        val messengerInRequestBody: RequestBody = messengerJson.toRequestBody(requestMediaType)

        var resultMessengerFullInfo: GetMessengerPrototypeDataBase? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = messengerService.addMessenger(messengerInRequestBody)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultMessengerFullInfo = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultMessengerFullInfo
    }
}