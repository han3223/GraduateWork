package com.example.documentsearch.api

import com.example.documentsearch.api.apiRequests.document.DocumentRequestServices
import com.example.documentsearch.api.apiRequests.file.FileRequestServices
import com.example.documentsearch.api.apiRequests.message.MessageRequestServices
import com.example.documentsearch.api.apiRequests.messenger.MessengerRequestServices
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServices
import com.example.documentsearch.api.apiRequests.tag.TagRequestServices
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ClientAPI {
    companion object {
        private const val ADDRESS = "http://10.10.10.13"
        private const val PORT = "8080"
        private const val BASE_URL = "$ADDRESS:$PORT/"

        val APIBasicService: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requestMediaType: MediaType? = "application/json".toMediaTypeOrNull()
    }

    object Messenger {
        private val javaProfileServices = MessengerRequestServices::class.java
        val messengerService: MessengerRequestServices = APIBasicService.create(javaProfileServices)
    }

    object Message {
        private val javaMessageServices = MessageRequestServices::class.java
        val messageServices: MessageRequestServices = APIBasicService.create(javaMessageServices)
    }

    object Tag {
        private val javaTagServices = TagRequestServices::class.java
        val tagService: TagRequestServices = APIBasicService.create(javaTagServices)
    }

    object Profile {
        private val javaProfileServices = ProfileRequestServices::class.java
        val profileService: ProfileRequestServices = APIBasicService.create(javaProfileServices)
    }

    object Document {
        private val javaDocumentServices = DocumentRequestServices::class.java
        val documentService: DocumentRequestServices = APIBasicService.create(javaDocumentServices)
    }

    object File {
        private val javaDocumentServices = FileRequestServices::class.java
        val fileService: FileRequestServices = APIBasicService.create(javaDocumentServices)
    }

    fun requestHandling(response: Response<ResponseBody>): String? {
        var result: String? = null
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null)
                result = responseBody.string()
        }
        else
            throw Exception(response.message())

        return result
    }
}






