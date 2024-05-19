package com.example.documentsearch.api.apiRequests.message

import com.example.documentsearch.api.apiRequests.message.delete.DeletionServiceInMessage
import com.example.documentsearch.api.apiRequests.message.get.ReceivingServiceInMessage
import com.example.documentsearch.api.apiRequests.message.post.AdditionsServiceInMessage
import com.example.documentsearch.api.apiRequests.message.put.UpdateServiceInMessage
import com.example.documentsearch.prototypes.MessagePrototype

class MessagesRequestServicesImpl {
    private val additionsServiceInMessageDelegate = AdditionsServiceInMessage()
    private val deletionServiceInMessageDelegate = DeletionServiceInMessage()
    private val receivingServiceInMessageDelegate = ReceivingServiceInMessage()
    private val updateServiceInMessageDelegate = UpdateServiceInMessage()

    suspend fun getMessagesFromMessenger(idMessenger: Long): MutableList<MessagePrototype> {
        return receivingServiceInMessageDelegate.getMessagesFromMessenger(idMessenger = idMessenger)
    }

    suspend fun addMessage(message: MessagePrototype): MessagePrototype? {
        return additionsServiceInMessageDelegate.addMessage(message = message)
    }
}