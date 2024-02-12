package com.example.documentsearch.api.apiRequests.profile.put

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.Profile.profileService

class UpdateServiceInProfile : ClientAPI() {
    suspend fun updatePersonalNameUsingEmail(email: String, personalName: String): Boolean {
        try {
            val response = profileService.updatePersonalNameUsingEmail(
                email = email,
                personalName = personalName
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить пользовательское имя")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление пользовательского имени произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return  false
    }

    suspend fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): Boolean {
        try {
            val response = profileService.updatePersonalInfoUsingEmail(
                email = email,
                personalInfo = personalInfo
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить информацию о профиле")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление информации о профиле произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): Boolean {
        try {
            val response = profileService.updateNumberPhoneUsingEmail(
                email = email,
                phoneNumber = phoneNumber
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить номер телефона")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление номера телефона произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): Boolean {
        try {
            val response = profileService.updateEmailUsingOldEmail(
                oldEmail = oldEmail,
                newEmail = newEmail
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить почту")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление почты произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updatePasswordUsingEmail(email: String, oldPassword: String, newPassword: String): Boolean {
        try {
            val response = profileService.updatePasswordUsingEmail(
                email = email,
                oldPassword = oldPassword,
                newPassword = newPassword
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить пароль")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление пароля произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updateTagsUsingEmail(email: String, tags: String): Boolean {
        try {
            val response = profileService.updateTagsUsingEmail(email = email, tags = tags)
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить теги")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление тегов произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun addTagUsingEmail(email: String, tag: String): Boolean {
        try {
            val response = profileService.addTagUsingEmail(email = email, tag = tag)
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось добавить тег пользователю")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление тега пользователю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun deleteTagUsingEmail(email: String, tag: String): Boolean {
        try {
            val response = profileService.deleteTagUsingEmail(email = email, tag = tag)
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось удалить тег пользователю")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на удаление тега пользователю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun addFriendUsingEmail(email: String, friend: String): Boolean {
        try {
            val response = profileService.addFriendUsingEmail(email = email, friend = friend)
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось добавить друга пользователю")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление друга пользователю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        try {
            val response = profileService.updatePasswordUsingPhoneNumber(
                phoneNumber = phoneNumber,
                newPassword = newPassword
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось обновить друзей пользователю")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на обновление друзей пользователю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }

    suspend fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        try {
            val response = profileService.updatePasswordUsingEmail(
                email = email,
                newPassword = newPassword
            )
            val result = requestHandling(response = response)?.toInt()
            if (result != 0)
                return true

            Log.i("Запрос", "Не удалось удалить друга пользователю")
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на удаление друга пользователю произошла ошибка! " +
                        "Ошибка: ${exception.message}"
            )
        }

        return false
    }
}