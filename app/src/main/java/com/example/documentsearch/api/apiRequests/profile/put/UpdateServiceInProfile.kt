package com.example.documentsearch.api.apiRequests.profile.put

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService

class UpdateServiceInProfile : ClientAPI() {
    suspend fun updatePersonalNameUsingEmail(email: String, personalName: String): String? {
        var resultPersonalName: String? = null
        try {
            resultPersonalName = requestHandling(profileService.updatePersonalNameUsingEmail(email, personalName))
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultPersonalName
    }

    suspend fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): String? {
        var resultPersonalInfo: String? = null
        try {
            resultPersonalInfo = requestHandling(profileService.updatePersonalInfoUsingEmail(email, personalInfo))
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultPersonalInfo
    }

    suspend fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): String? {
        var resultNumberPhone: String? = null
        try {
            resultNumberPhone = requestHandling(profileService.updateNumberPhoneUsingEmail(email, phoneNumber))
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultNumberPhone
    }

    suspend fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): String? {
        var resultEmail: String? = null
        try {
            resultEmail = requestHandling(profileService.updateEmailUsingOldEmail(oldEmail, newEmail))
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultEmail
    }

    suspend fun updatePasswordUsingEmail(email: String, oldPassword: String, newPassword: String): String? {
        var resultPassword: String? = null
        try {
            resultPassword = requestHandling(profileService.updatePasswordUsingEmail(email, oldPassword, newPassword))
        } catch (exception: Exception) {
            println(exception.message)
        }

        return resultPassword
    }

    suspend fun updateTagsUsingEmail(email: String, tags: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.updateTagsUsingEmail(email, tags))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }

    suspend fun addTagUsingEmail(email: String, tag: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.addTagUsingEmail(email, tag))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }

    suspend fun deleteTagUsingEmail(email: String, tag: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.deleteTagUsingEmail(email, tag))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }

    suspend fun addFriendUsingEmail(email: String, friend: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.addFriendUsingEmail(email, friend))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }

    suspend fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.updatePasswordUsingPhoneNumber(phoneNumber, newPassword))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }

    suspend fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        var isSuccessful = false
        try {
            val response = requestHandling(profileService.updatePasswordUsingEmail(email, newPassword))
            if (response != null)
                isSuccessful = true
        } catch (exception: Exception) {
            println(exception.message)
        }

        return isSuccessful
    }
}