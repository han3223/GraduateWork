package com.example.documentsearch.api.apiRequests.profile.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInProfile : ClientAPI() {
    suspend fun getProfileUsingEmail(email: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null

        try {
            val response = profileService.getProfileUsingEmail(email = email)
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение профиля по почте вернул пустое значение")
            else
                resultProfile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение профиля по почте произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultProfile
    }

    suspend fun getProfileUsingPhoneNumber(phoneNumber: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null

        try {
            val response = profileService.getProfileUsingPhoneNumber(phoneNumber = phoneNumber)
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение профиля по номеру телефона вернул пустое значение")
            else
                resultProfile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение профиля по номеру телефона произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultProfile
    }

    suspend fun getProfileUsingEmailAndPassword(email: String, password: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null

        try {
            val response = profileService.getProfileUsingEmailAndPassword(
                email = email,
                password = password
            )
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение профиля по почте и паролю вернул пустое значение")
            else
                resultProfile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение профиля по почте и паролю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultProfile
    }

    suspend fun getRecoveryCodeUsingLastNameAndPhoneNumber(lastName: String, phoneNumber: String): Int? {
        var resultCode: Int? = null

        try {
            val response = profileService.getRecoveryCodeUsingLastNameAndPhoneNumber(
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            val resultCodeInString = requestHandling(response = response)
            if (resultCodeInString == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение кода восстановления вернул пустое значение")
            else
                resultCode = resultCodeInString.toInt()
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение кода восстановления произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultCode
    }

    suspend fun getRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        var resultCode: Int? = null

        try {
            val response = profileService.getRecoveryCodeUsingLastNameAndEmail(
                lastName = lastName,
                email = email
            )
            val resultCodeInString = requestHandling(response = response)
            Log.i("Value", resultCodeInString)
            if (resultCodeInString == Status.ERROR.status)
                Log.i("Запрос", "Запрос на получение кода восстановления вернул пустое значение")
            else
                resultCode = resultCodeInString.toInt()
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение кода восстановления произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultCode
    }

    suspend fun getVerificationCode(email: String): String {
        var code = ""

        try {
            val response = profileService.getVerificationCode(email)
            val json = requestHandling(response = response)
            code = json
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение профиля по персональному имени произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return code
    }

    suspend fun getProfileUsingPersonalName(personalName: String): UserProfilePrototype? {
        var resultProfile: UserProfilePrototype? = null

        try {
            val response = profileService.getProfileUsingPersonalName(personalName = personalName)
            val json = requestHandling(response = response)

            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение профиля по персональному имени вернул пустое значение")
            else
                resultProfile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение профиля по персональному имени произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return resultProfile
    }
}