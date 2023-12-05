package com.example.documentsearch.api.apiRequests.profile.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.UserProfilePrototype
import kotlinx.serialization.json.Json

class ReceivingServiceInProfile : ClientAPI() {
    suspend fun getProfileUsingEmail(email: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.getProfileUsingEmail(email))
            if (response != null)
                resultProfile = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.printStackTrace())
        }

        return resultProfile
    }

    suspend fun getProfileUsingPhoneNumber(phoneNumber: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.getProfileUsingPhoneNumber(phoneNumber))
            if (response != null)
                resultProfile = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultProfile
    }

    suspend fun getProfileUsingPersonalName(personalName: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.getProfileUsingPersonalName(personalName))
            if (response != null)
                resultProfile = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.printStackTrace())
        }

        return resultProfile
    }

    suspend fun getProfileUsingEmailAndPassword(
        email: String,
        password: String
    ): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null
        try {
            val response = requestHandling(profileService.getProfileUsingEmailAndPassword(email, password))
            if (response != null)
                resultProfile = Json.decodeFromString(response)
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultProfile
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        var resultCode: Int? = null
        try {
            val response = requestHandling(profileService.getProfileRecoveryCodeUsingLastNameAndEmail(lastName, email))
            if (response != null)
                resultCode = response.toInt()
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultCode
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(
        lastName: String,
        phoneNumber: String
    ): Int? {
        var resultCode: Int? = null
        try {
            val response = requestHandling(profileService.getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName, phoneNumber))
            if (response != null)
                resultCode = response.toInt()
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultCode
    }
}