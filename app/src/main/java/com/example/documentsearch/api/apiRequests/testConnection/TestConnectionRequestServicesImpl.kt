package com.example.documentsearch.api.apiRequests.testConnection

import com.example.documentsearch.api.apiRequests.testConnection.get.ReceivingServiceInTestConnection

class TestConnectionRequestServicesImpl {
    private val receivingServiceInTestConnectionDelegate = ReceivingServiceInTestConnection()

    suspend fun getConnection(): Boolean {
        return receivingServiceInTestConnectionDelegate.getConnection()
    }
}