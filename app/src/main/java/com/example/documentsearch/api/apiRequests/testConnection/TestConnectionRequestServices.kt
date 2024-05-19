package com.example.documentsearch.api.apiRequests.testConnection

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface TestConnectionRequestServices {
    @GET("test_connection")
    suspend fun getConnection(): Response<ResponseBody>
}