package com.example.documentsearch.api.apiRequests.messenger

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessengerRequestServices {
    @GET("messengers-get/by-participants")
    suspend fun getMessengerByParticipants(@Query("participants") participants: String): Response<ResponseBody>

    @GET("messengers-get/by-user")
    suspend fun getAllMessengersUsingUserId(@Query("user") userId: Long): Response<ResponseBody>

    @POST("messengers-post/add")
    suspend fun addMessenger(@Body jsonPrototypeMessenger: RequestBody): Response<ResponseBody>
}