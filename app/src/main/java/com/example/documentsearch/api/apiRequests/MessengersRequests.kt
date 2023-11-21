package com.example.documentsearch.api.apiRequests

import com.example.documentsearch.api.ConnectionApi
import com.example.documentsearch.dataClasses.AddMessenger
import com.example.documentsearch.dataClasses.GetMessenger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MessengersRequests {
    companion object {
        val MESSENGER_SERVICE: IMessengerRequests = ConnectionApi.RETROFIT.create(IMessengerRequests::class.java)
    }

    suspend fun getAllMessengersFromUser(userId: Long): MutableList<GetMessenger>? {
        var data: MutableList<GetMessenger>? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                MESSENGER_SERVICE.getAllMessengersFromUser(userId.toString())
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

    suspend fun addMessenger(messenger: AddMessenger): GetMessenger? {
        var data: GetMessenger? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                MESSENGER_SERVICE.addMessenger(Json.encodeToString(messenger).toRequestBody("application/json".toMediaTypeOrNull()))
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString<GetMessenger>(response.body()!!.string())
            }
        }

        return data
    }

    suspend fun deleteMessenger(userId: String, interlocutorId: String): Int? {
        var data: Int? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                MESSENGER_SERVICE.deleteMessenger(userId, interlocutorId)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = response.body()!!.string().toInt()
            }
        }

        return data
    }
}