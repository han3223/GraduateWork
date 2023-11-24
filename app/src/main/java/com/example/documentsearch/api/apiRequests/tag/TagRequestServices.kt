package com.example.documentsearch.api.apiRequests.tag

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface TagRequestServices {
    @GET("requests/tag/getTagsByType?type=Документы")
    suspend fun getDocumentTags(): Response<ResponseBody>

    @GET("requests/tag/getTagsByType?type=Пользователи")
    suspend fun getProfileTags(): Response<ResponseBody>
}