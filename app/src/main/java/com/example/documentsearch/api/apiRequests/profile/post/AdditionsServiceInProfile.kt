package com.example.documentsearch.api.apiRequests.profile.post

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.ProfilePrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInProfile : ClientAPI() {
    fun addProfile(profile: ProfilePrototype): ProfilePrototype? {
        val profileJson: String = Json.encodeToString(profile)
        val profileInRequestBody: RequestBody = profileJson.toRequestBody(requestMediaType)

        var resultProfile: ProfilePrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.addProfile(profileInRequestBody)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultProfile = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultProfile
    }
}