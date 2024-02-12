package com.example.documentsearch.api.apiRequests.file.post

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.File.fileService
import com.example.documentsearch.prototypes.FilePrototype
import kotlinx.serialization.json.Json.Default.decodeFromString
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdditionsServiceInFile : ClientAPI() {
    suspend fun addFile(file: ByteArray): FilePrototype? {
        var resultFile: FilePrototype? = null

        val fileInRequestBody: RequestBody = file.toRequestBody(contentType = requestMediaType)
        try {
            val response = fileService.addFile(fileInByteArray = fileInRequestBody)
            val json = requestHandling(response = response)
            if (json == null)
                Log.i("Запрос", "Запрос на добавление файла вернул пустое значение")
            else
                resultFile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на добавление файла произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultFile
    }
}