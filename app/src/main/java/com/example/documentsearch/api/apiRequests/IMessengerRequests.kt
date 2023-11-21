package com.example.documentsearch.api.apiRequests

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IMessengerRequests {
    @GET("requests/messenger/getMessengersByUser")
    suspend fun getAllMessengersFromUser(@Query("user") userId: String): Response<ResponseBody>

    @POST("requests/messenger/addMessenger")
    suspend fun addMessenger(@Body jsonMessenger: RequestBody): Response<ResponseBody>

    @DELETE("requests/messenger/deleteMessenger")
    suspend fun deleteMessenger(
        @Query("userId") userId: String,
        @Query("interlocutorId") interlocutorId: String
    ): Response<ResponseBody>
}