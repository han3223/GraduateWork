package com.example.documentsearch.api.apiRequests.tag

import android.annotation.SuppressLint
import com.example.documentsearch.api.apiRequests.tag.delete.DeletionServiceInTag
import com.example.documentsearch.api.apiRequests.tag.get.ReceivingServiceInTag
import com.example.documentsearch.api.apiRequests.tag.post.AdditionsServiceInTag
import com.example.documentsearch.api.apiRequests.tag.put.UpdateServiceInTag
import com.example.documentsearch.prototypes.TagPrototype
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagRequestServicesImpl {
    private val coroutines = CoroutineScope(Dispatchers.Main)

    private val additionsServiceInTagDelegate = AdditionsServiceInTag()
    private val deletionServiceInTagDelegate = DeletionServiceInTag()
    private val receivingServiceInTagDelegate = ReceivingServiceInTag()
    private val updateServiceInTagDelegate = UpdateServiceInTag()

    @SuppressLint("CoroutineCreationDuringComposition")
    fun getDocumentTags(): List<TagPrototype> {
        var documentTags = listOf<TagPrototype>()
        coroutines.launch {
            documentTags = receivingServiceInTagDelegate.getDocumentTags()
        }
        return documentTags
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    fun getProfileTags(): List<TagPrototype> {
        var profileTags = listOf<TagPrototype>()
        coroutines.launch {
            profileTags = receivingServiceInTagDelegate.getProfileTags()
        }
        return profileTags
    }
}