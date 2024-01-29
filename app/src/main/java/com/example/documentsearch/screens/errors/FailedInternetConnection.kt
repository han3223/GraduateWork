package com.example.documentsearch.screens.errors

import androidx.compose.runtime.Composable
import com.example.documentsearch.R

class FailedInternetConnection {
    private val image = R.drawable.error_internet_connection
    private val titleError = "Не можем подключиться"
    private val descriptionError = "Возможно у вас отсутствует  подключение к интернету. Проверьте соединение и обновите страницу.\n"
    private val errorsFactory = ErrorsFactory(image = image, titleError = titleError, descriptionError = descriptionError)

    @Composable
    fun Content() {
        errorsFactory.Content()
    }
}