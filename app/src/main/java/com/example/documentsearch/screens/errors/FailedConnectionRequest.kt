package com.example.documentsearch.screens.errors

import androidx.compose.runtime.Composable
import com.example.documentsearch.R

class FailedConnectionRequest {
    private val image = R.drawable.error_internet_connection
    private val titleError = "Отсутствует соединение с сервером"
    private val descriptionError = "Не волнуйтесь, мы уже всё чиним. Попробуйте обновить страницу\n"
    private val errorsFactory = ErrorsFactory(image = image, titleError = titleError, descriptionError = descriptionError)

    @Composable
    fun Content() {
        errorsFactory.Content()
    }
}