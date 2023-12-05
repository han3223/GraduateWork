package com.example.documentsearch.api.apiRequests.profile.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import kotlinx.serialization.json.Json

class ReceivingServiceInAnotherProfile : ClientAPI() {
    suspend fun getAllAnotherProfile(): List<AnotherUserProfilePrototype> {
        var resultAnotherUsers = listOf<AnotherUserProfilePrototype>()
        try {
            val response = requestHandling(profileService.getAllProfileWithoutPassword())
            if (response != null)
                resultAnotherUsers = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultAnotherUsers
    }

    suspend fun getAnotherProfileUsingId(idProfile: Long): AnotherUserProfilePrototype? {
        var resultAnotherUser: AnotherUserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.getAnotherProfileUsingId(idProfile))
            if (response != null)
                resultAnotherUser = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultAnotherUser
    }
}