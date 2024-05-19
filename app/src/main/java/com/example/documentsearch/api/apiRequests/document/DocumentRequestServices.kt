package com.example.documentsearch.api.apiRequests.document

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DocumentRequestServices {
    @GET("documents-get/get-all")
    suspend fun getAllDocuments(): Response<ResponseBody>

    @GET("documents-get/get-by-user-id")
    suspend fun getDocumentsByUserId(@Query("user_id") userId: Long): Response<ResponseBody>

    @GET("documents-get/get-by-title-category-date-tags")
    suspend fun getDocuments(
        @Query("title") title: String,
        @Query("category") category: String?,
        @Query("dateAfter") dateAfter: String?,
        @Query("dateBefore") dateBefore: String?,
        @Query("tags") tags: String?,
    ): Response<ResponseBody>

    @POST("documents-post/add")
    suspend fun addDocument(@Body jsonPrototypeDocument: RequestBody): Response<ResponseBody>
}