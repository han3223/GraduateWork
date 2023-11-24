package com.example.documentsearch.api.apiRequests.profile.get

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.ProfilePrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ReceivingServiceInProfile : ClientAPI() {
    fun getProfileUsingEmail(email: String): ProfilePrototype? {
        var resultProfile: ProfilePrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileUsingEmail(email)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultProfile = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.printStackTrace())
            }
        }

        return resultProfile
    }

    fun getProfileUsingPhoneNumber(phoneNumber: String): ProfilePrototype? {
        var resultProfile: ProfilePrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileUsingPhoneNumber(phoneNumber)
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

    fun getProfileUsingPersonalName(personalName: String): ProfilePrototype? {
        var resultProfile: ProfilePrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileUsingPersonalName(personalName)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultProfile = Json.decodeFromString(responseBody.string())
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.printStackTrace())
            }
        }

        return resultProfile
    }

    fun getProfileUsingEmailAndPassword(email: String, password: String): ProfilePrototype? {
        var resultProfile: ProfilePrototype? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileUsingEmailAndPassword(email, password)
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

    fun getProfileRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        var resultCode: Int? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileRecoveryCodeUsingLastNameAndEmail(lastName, email)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultCode = responseBody.string().toInt()
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultCode
    }

    fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName: String, phoneNumber: String): Int? {
        var resultCode: Int? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName, phoneNumber)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultCode = responseBody.string().toInt()
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultCode
    }
}