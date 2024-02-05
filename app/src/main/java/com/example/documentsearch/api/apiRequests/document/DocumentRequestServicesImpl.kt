package com.example.documentsearch.api.apiRequests.document

import com.example.documentsearch.api.apiRequests.document.get.ReceivingServiceInDocument
import com.example.documentsearch.api.apiRequests.document.post.AdditionsServiceInDocument
import com.example.documentsearch.prototypes.AddDocumentPrototype
import com.example.documentsearch.prototypes.DocumentPrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DocumentRequestServicesImpl {
    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val additionsServiceInDocumentDelegate = AdditionsServiceInDocument()
    private val receivingServiceInDocument = ReceivingServiceInDocument()


    suspend fun getAllDocuments(): List<DocumentPrototype> {
        return receivingServiceInDocument.getAllDocuments()
    }

    suspend fun addDocument(document: AddDocumentPrototype): String {
        return additionsServiceInDocumentDelegate.addDocument(document)
    }

    suspend fun getDocuments(
        title: String,
        category: String?,
        dateAfter: String?,
        dateBefore: String?,
        tags: String?
    ): List<DocumentPrototype> {
        return receivingServiceInDocument.getDocuments(title, category, dateAfter, dateBefore, tags)
    }
}