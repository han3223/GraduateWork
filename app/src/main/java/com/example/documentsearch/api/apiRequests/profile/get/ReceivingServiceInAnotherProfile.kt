package com.example.documentsearch.api.apiRequests.profile.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.AnotherUserPrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ReceivingServiceInAnotherProfile : ClientAPI() {
    fun getAllAnotherProfile(): List<AnotherUserPrototype> {
        var resultAnotherUsers = listOf<AnotherUserPrototype>()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getAllProfileWithoutPassword()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultAnotherUsers = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultAnotherUsers
    }

    fun getAnotherProfileUsingId(idProfile: Long): AnotherUserPrototype? {
        var resultAnotherUser: AnotherUserPrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getAnotherProfileUsingId(idProfile)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultAnotherUser = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultAnotherUser
    }
}