package com.example.documentsearch.api.apiRequests.profile.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.UserProfilePrototype
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInProfile : ClientAPI() {
    suspend fun addProfile(profile: UserProfilePrototype): UserProfilePrototype? {
        val profileJson: String = Json.encodeToString(profile)
        val profileInRequestBody: RequestBody = profileJson.toRequestBody(requestMediaType)

        var resultProfile: UserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.addProfile(profileInRequestBody))
            if (response != null)
                resultProfile = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultProfile
    }
}