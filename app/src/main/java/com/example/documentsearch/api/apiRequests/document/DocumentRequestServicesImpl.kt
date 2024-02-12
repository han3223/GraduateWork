package com.example.documentsearch.api.apiRequests.document

import com.example.documentsearch.api.apiRequests.document.get.ReceivingServiceInDocument
import com.example.documentsearch.api.apiRequests.document.post.AdditionsServiceInDocument
import com.example.documentsearch.prototypes.DocumentPrototype

class DocumentRequestServicesImpl {
    private val additionsServiceInDocumentDelegate = AdditionsServiceInDocument()
    private val receivingServiceInDocumentDelegate = ReceivingServiceInDocument()

    suspend fun getAllDocuments(): List<DocumentPrototype> {
        return receivingServiceInDocumentDelegate.getAllDocuments()
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