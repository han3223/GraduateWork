package com.example.documentsearch.api.apiRequests.profile.put

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateServiceInProfile : ClientAPI() {
    fun updatePersonalNameUsingEmail(email: String, personalName: String): String? {
        var resultPersonalName: String? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updatePersonalNameUsingEmail(email, personalName)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultPersonalName = personalName
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultPersonalName
    }

    fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): String? {
        var resultPersonalInfo: String? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updatePersonalInfoUsingEmail(email, personalInfo)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultPersonalInfo = personalInfo
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultPersonalInfo
    }

    fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): String? {
        var resultNumberPhone: String? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updateNumberPhoneUsingEmail(email, phoneNumber)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultNumberPhone = phoneNumber
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultNumberPhone
    }

    fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): String? {
        var resultEmail: String? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updateEmailUsingOldEmail(oldEmail, newEmail)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultEmail = newEmail
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultEmail
    }

    fun updatePasswordUsingEmail(email: String, oldPassword: String, newPassword: String): String? {
        var resultPassword: String? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updatePasswordUsingEmail(email, oldPassword, newPassword)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        resultPassword = newPassword
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return resultPassword
    }

    fun updateTagsUsingEmail(email: String, tags: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updateTagsUsingEmail(email, tags)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }

    fun addTagUsingEmail(email: String, tag: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.addTagUsingEmail(email, tag)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }

    fun deleteTagUsingEmail(email: String, tag: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.deleteTagUsingEmail(email, tag)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }

    fun addFriendUsingEmail(email: String, friend: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.addFriendUsingEmail(email, friend)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }

    fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updatePasswordUsingPhoneNumber(phoneNumber, newPassword)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }

    fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        var isSuccessful = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = profileService.updatePasswordUsingEmail(email, newPassword)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null)
                        isSuccessful = true
                }
                else
                    throw Exception(response.message())
            } catch (exception: Exception) {
                println(exception.message)
            }
        }

        return isSuccessful
    }
}