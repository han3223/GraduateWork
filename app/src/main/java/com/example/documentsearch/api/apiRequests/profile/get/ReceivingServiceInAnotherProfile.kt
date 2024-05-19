package com.example.documentsearch.api.apiRequests.profile.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInAnotherProfile : ClientAPI() {
    suspend fun getAllAnotherProfile(): List<UserProfilePrototype> {
        var resultAnotherUsers = listOf<UserProfilePrototype>()

        try {
            val response = profileService.getAllProfileWithoutPassword()
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение всех пользователей вернул пустое значение")
            else
                resultAnotherUsers = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение всех пользователей произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultAnotherUsers
    }

    suspend fun getAnotherProfileUsingId(idProfile: Long): UserProfilePrototype? {
        var resultAnotherUser: UserProfilePrototype? = null

        try {
            val response = profileService.getAnotherProfileUsingId(idProfile = idProfile)
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение пользователя вернул пустое значение")
            else
                resultAnotherUser = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение пользователя произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultAnotherUser
    }
}