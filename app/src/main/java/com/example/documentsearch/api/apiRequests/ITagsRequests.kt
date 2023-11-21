package com.example.documentsearch.api.apiRequests

import com.example.documentsearch.dataClasses.Tag
import retrofit2.http.GET

interface ITagsRequests {
    @GET("requests/tag/getTagsByType?type=Документы")
    suspend fun getDocumentTags(): List<Tag>

    @GET("requests/tag/getTagsByType?type=Пользователи")
    suspend fun getProfileTags(): List<Tag>
}