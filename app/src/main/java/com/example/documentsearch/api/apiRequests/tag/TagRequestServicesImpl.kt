package com.example.documentsearch.api.apiRequests.tag

import android.annotation.SuppressLint
import com.example.documentsearch.api.apiRequests.tag.delete.DeletionServiceInTag
import com.example.documentsearch.api.apiRequests.tag.get.ReceivingServiceInTag
import com.example.documentsearch.api.apiRequests.tag.post.AdditionsServiceInTag
import com.example.documentsearch.api.apiRequests.tag.put.UpdateServiceInTag
import com.example.documentsearch.prototypes.TagPrototype

class TagRequestServicesImpl {
    private val additionsServiceInTagDelegate = AdditionsServiceInTag()
    private val deletionServiceInTagDelegate = DeletionServiceInTag()
    private val receivingServiceInTagDelegate = ReceivingServiceInTag()
    private val updateServiceInTagDelegate = UpdateServiceInTag()

    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getDocumentTags(): List<TagPrototype> {
        return receivingServiceInTagDelegate.getDocumentTags()
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    suspend fun getProfileTags(): List<TagPrototype> {
        return receivingServiceInTagDelegate.getProfileTags()
    }
}