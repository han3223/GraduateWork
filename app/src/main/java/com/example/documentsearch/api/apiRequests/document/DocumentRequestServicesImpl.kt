package com.example.documentsearch.api.apiRequests.document

import com.example.documentsearch.api.apiRequests.document.get.ReceivingServiceInDocument
import com.example.documentsearch.api.apiRequests.document.post.AdditionsServiceInDocument
import com.example.documentsearch.prototypes.DocumentPrototype
import com.example.documentsearch.ui.theme.isDocumentLoad

class DocumentRequestServicesImpl {
    private val additionsServiceInDocumentDelegate = AdditionsServiceInDocument()
    private val receivingServiceInDocumentDelegate = ReceivingServiceInDocument()

    suspend fun getAllDocuments(): List<DocumentPrototype> {
        val result = receivingServiceInDocumentDelegate.getAllDocuments()
        isDocumentLoad.value = true
        return  result
    }

    suspend fun getDocumentsByUserId(userId: Long): List<DocumentPrototype> {
        return receivingServiceInDocumentDelegate.getDocumentsByUserId(userId)
    }

    suspend fun addDocument(document: DocumentPrototype): DocumentPrototype? {
        return additionsServiceInDocumentDelegate.addDocument(document = document)
    }

    suspend fun getDocuments(
        title: String,
        category: String?,
        dateAfter: String?,
        dateBefore: String?,
        tags: List<String>?
    ): List<DocumentPrototype> {
        return receivingServiceInDocumentDelegate.getDocuments(
            title = title,
            category = category,
            dateAfter = dateAfter,
            dateBefore = dateBefore,
            tags = tags
        )
    }
}