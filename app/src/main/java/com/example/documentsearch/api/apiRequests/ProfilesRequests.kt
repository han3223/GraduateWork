package com.example.documentsearch.api.apiRequests

import com.example.documentsearch.api.ConnectionApi
import com.example.documentsearch.dataClasses.AnotherUser
import com.example.documentsearch.dataClasses.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ProfilesRequests {
    companion object {
        val PROFILE_SERVICE: IProfileRequests = ConnectionApi.RETROFIT.create(IProfileRequests::class.java)
    }

    suspend fun addProfile(profile: Profile): Profile? {
        var data: Profile? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.addProfile(Json.encodeToString(profile).toRequestBody("application/json".toMediaTypeOrNull()))
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString<Profile>(response.body()!!.string())
            }
        }

        return data
    }

    suspend fun getAnotherProfileById(id: Long): AnotherUser? {
        var data: AnotherUser? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getAnotherProfileById(id.toString())
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString<AnotherUser>(response.body()!!.string())
            }
        }

        return data
    }

    suspend fun getProfile(email: String, password: String): Profile? {
        var data: Profile? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfile(email, password)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString<Profile>(response.body()!!.string())
            }
        }

        return data
    }


    suspend fun getAllProfileWithoutPassword(): List<AnotherUser>? {
        var data: List<AnotherUser>? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getAllProfileWithoutPassword()
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = Json.decodeFromString<List<AnotherUser>>(response.body()!!.string())
            }
        }

        return data
    }

    suspend fun getProfileByEmail(email: String): Profile? {
        var data: Profile? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfileByEmail(email)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = try {
                    Json.decodeFromString<Profile>(response.body()!!.string())
                } catch (exception: Exception) {
                    null
                }
            }
        }

        return data
    }

    suspend fun getProfileByPhoneNumber(phoneNumber: String): Profile? {
        var data: Profile? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfileByPhoneNumber(phoneNumber)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = try {
                    Json.decodeFromString<Profile>(response.body()!!.string())
                } catch (exception: Exception) {
                    null
                }
            }
        }

        return data
    }

    suspend fun getProfileByPersonalName(personalName: String): Profile? {
        var data: Profile? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfileByPersonalName(personalName)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = try {
                    Json.decodeFromString<Profile>(response.body()!!.string())
                } catch (exception: Exception) {
                    null
                }
            }
        }

        return data
    }

    suspend fun getProfileByFullNameAndPhoneNumber(lastName: String, phoneNumber: String): Int? {
        var data: Int? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfileByFullNameAndPhoneNumber(lastName, phoneNumber)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = try {
                    response.body()!!.string().toInt()
                } catch (exception: Exception) {
                    null
                }
            }
        }

        return data
    }

    suspend fun getProfileByFullNameAndEmail(lastName: String, email: String): Int? {
        var data: Int? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.getProfileByFullNameAndEmail(lastName, email)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = try {
                    response.body()!!.string().toInt()
                } catch (exception: Exception) {
                    null
                }
            }
        }

        return data
    }

    suspend fun updatePersonalName(email: String, personalName: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updatePersonalName(email, personalName)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = personalName
            }
        }

        return data
    }

    suspend fun updatePersonalInfo(email: String, personalInfo: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updatePersonalInfo(email, personalInfo)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = personalInfo
            }
        }

        return data
    }

    suspend fun updatePhoneNumber(email: String, phoneNumber: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updateNumberPhone(email, phoneNumber)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = phoneNumber
            }
        }

        return data
    }

    suspend fun updateEmail(oldEmail: String, newEmail: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updateEmail(oldEmail, newEmail)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = newEmail
            }
        }

        return data
    }

    suspend fun updatePassword(email: String, oldPassword: String, newPassword: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updatePassword(email, oldPassword, newPassword)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = newPassword
            }
        }

        return data
    }

    suspend fun updateTags(email: String, tags: String): Boolean {
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updateTags(email, tags)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                return true
            }
        }

        return false
    }

    suspend fun addTag(email: String, tag: String): Boolean {
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.addTag(email, tag)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                return true
            }
        }

        return false
    }

    suspend fun deleteTag(email: String, tag: String): Boolean {
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.deleteTag(email, tag)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                return true
            }
        }

        return false
    }


    suspend fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updatePasswordUsingPhoneNumber(phoneNumber, newPassword)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = "Success"
            }
        }

        return data
    }

    suspend fun updatePasswordUsingEmail(email: String, newPassword: String): String? {
        var data: String? = null
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.updatePasswordUsingEmail(email, newPassword)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                data = "Success"
            }
        }

        return data
    }

    suspend fun addFriend(email: String, friend: String): Boolean {
        val response = CoroutineScope(Dispatchers.IO).async {
            try {
                PROFILE_SERVICE.addFriend(email, friend)
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
                null
            }
        }.await()

        if (response?.isSuccessful == true) {
            if (response.body() != null) {
                return true
            }
        }

        return false
    }
}