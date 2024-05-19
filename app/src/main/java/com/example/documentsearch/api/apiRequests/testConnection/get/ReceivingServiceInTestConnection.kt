package com.example.documentsearch.api.apiRequests.testConnection.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Connection.connectionService
import com.example.documentsearch.ui.theme.Status

class ReceivingServiceInTestConnection: ClientAPI() {
    suspend fun getConnection(): Boolean {
        return try {
            val response = connectionService.getConnection()
            val json = requestHandling(response = response)
            json == Status.OK.status
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на вывод всех документов произошла ошибка! Ошибка: ${exception.message}"
            )
            false
        }
    }
}