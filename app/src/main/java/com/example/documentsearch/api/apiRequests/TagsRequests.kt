package com.example.documentsearch.api.apiRequests

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.documentsearch.api.ConnectionApi
import com.example.documentsearch.dataClasses.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagsRequests {
    companion object {
        val TAG_SERVICE: ITagsRequests = ConnectionApi.RETROFIT.create(ITagsRequests::class.java)
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun getDocumentTags(): List<Tag> {
        val dataState = remember { mutableStateOf<List<Tag>?>(null) }

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val data = TAG_SERVICE.getDocumentTags()
                dataState.value = data
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
            }
        }

        // Отображение полученных данных
        dataState.value?.let { data ->
            return data
        }

        return listOf()
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun getProfileTags(): List<Tag> {
        val scope = rememberCoroutineScope()
        val dataState = remember { mutableStateOf<List<Tag>?>(null) }

        scope.launch {
            try {
                val data = TAG_SERVICE.getProfileTags()
                dataState.value = data
            } catch (e: Exception) {
                println("Возникла ошибка")
                println(e.printStackTrace())
            }
        }

        // Отображение полученных данных
        dataState.value?.let { data ->
            return data
        }

        return listOf()
    }
}