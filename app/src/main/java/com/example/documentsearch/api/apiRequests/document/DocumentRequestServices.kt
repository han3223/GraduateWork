package com.example.documentsearch.api.apiRequests.document

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DocumentRequestServices {
    @POST("requests/document/addDocument")
    suspend fun addDocument(@Body jsonPrototypeDocument: RequestBody): Response<ResponseBody>

    @GET("/requests/document/getAllDocuments")
    suspend fun getAllDocuments(): Response<ResponseBody>

    @GET("/requests/document/getDocumentsByTitleAndCategoryAndDateAndTags")
    suspend fun getDocuments(
        @Query("title") title: String,
        @Query("category") category: String?,
        @Query("dateAfter") dateAfter: String?,
        @Query("dateBefore") dateBefore: String?,
        @Query("tags") tags: String?,
    ): Response<ResponseBody>
}