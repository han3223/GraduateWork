package com.example.documentsearch.api.apiRequests

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IMessageRequests {
    @POST("requests/message/addMessage")
    suspend fun addMessage(@Body jsonMessage: RequestBody): Response<ResponseBody>

    @GET("requests/message/getMessagesByMessenger")
    suspend fun getMessagesByMessenger(
        @Query("messenger") id: String,
    ): Response<ResponseBody>
}