package com.example.documentsearch.api.apiRequests.file

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FileRequestServices {
    @GET("requests/file/getFile")
    suspend fun getFile(@Query("id") idFile: Long): Response<ResponseBody>

    @POST("requests/file/addFile")
    suspend fun addFile(@Body fileInByteArray: RequestBody): Response<ResponseBody>
}