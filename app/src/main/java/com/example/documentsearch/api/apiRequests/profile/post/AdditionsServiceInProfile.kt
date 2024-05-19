package com.example.documentsearch.api.apiRequests.profile.post

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInProfile : ClientAPI() {
    suspend fun addProfile(profile: UserProfilePrototype): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null

        val profileJson: String = Json.encodeToString(profile)
        val profileInRequestBody: RequestBody = profileJson.toRequestBody(requestMediaType)
        try {
            val response = profileService.addProfile(jsonPrototypeProfile = profileInRequestBody)
            val json = requestHandling(response = response)
            if (json == Status.ERROR.status)
                Log.i("Запрос", "Запрос на добавление пользователя вернул пустое значение")
            else
                resultProfile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление пользователя произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultProfile
    }
}