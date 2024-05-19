package com.example.documentsearch.api.apiRequests.file.get

import android.util.Log
import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.ClientAPI.File.fileService
import com.example.documentsearch.prototypes.FilePrototype
import com.example.documentsearch.ui.theme.Status
import kotlinx.serialization.json.Json.Default.decodeFromString

class ReceivingServiceInFile : ClientAPI() {
    suspend fun getFile(id: Long): FilePrototype? {
        var resultFile: FilePrototype? = null
        try {
            val response = fileService.getFile(idFile = id)
            val json = requestHandling(response = response)
            if (json == Status.EMPTY.status)
                Log.i("Запрос", "Запрос на получение файла вернул пустое значение")
            else
                resultFile = decodeFromString(string = json)
        } catch (exception: Exception) {
            Log.e(
                "Ошибка выполнения запроса!",
                "В запросе на получение файла произошла ошибка! Ошибка: ${exception.message}"
            )
        }

        return resultFile
    }
}