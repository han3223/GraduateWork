package com.example.documentsearch.api.apiRequests

import com.example.documentsearch.api.ConnectionApi
import com.example.documentsearch.dataClasses.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MessageRequests {
    companion object {
        val MESSAGE_SERVICE: IMessageRequests = ConnectionApi.RETROFIT.create(IMessageRequests::class.java)
    }

    suspend fun getMessagesByMessenger(messengerId: Long): MutableList<Message> {
        var data: MutableList<Message> = mutableListOf()
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                MESSAGE_SERVICE.getMessagesByMessenger(messengerId.toString())
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString(response.body()!!.string())
            }
        }

        return data
    }

    suspend fun addMessage(message: Message): Boolean {
        var successful: Boolean = false
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                MESSAGE_SERVICE.addMessage(Json.encodeToString(message).toRequestBody("application/json".toMediaTypeOrNull()))
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                successful = true
            }
        }

        return successful
    }
}