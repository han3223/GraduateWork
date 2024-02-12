package com.example.documentsearch.api.apiRequests.file

import com.example.documentsearch.api.apiRequests.file.get.ReceivingServiceInFile
import com.example.documentsearch.api.apiRequests.file.post.AdditionsServiceInFile
import com.example.documentsearch.prototypes.FilePrototype

class FileRequestServicesImpl {
    private val additionsServiceInFileDelegate = AdditionsServiceInFile()
    private val receivingServiceInFileDelegate = ReceivingServiceInFile()

    suspend fun getFile(id: Long): FilePrototype? {
        return receivingServiceInFileDelegate.getFile(id = id)
    }

    suspend fun addFile(file: ByteArray): FilePrototype? {
        return additionsServiceInFileDelegate.addFile(file = file)
    }
}