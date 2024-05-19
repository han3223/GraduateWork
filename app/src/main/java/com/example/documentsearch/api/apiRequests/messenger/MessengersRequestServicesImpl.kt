package com.example.documentsearch.api.apiRequests.messenger

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.messenger.delete.DeletionServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.get.ReceivingServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.post.AdditionsServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.put.UpdateServiceInMessenger
import com.example.documentsearch.prototypes.ChatData

class MessengersRequestServicesImpl : ClientAPI() {
    private val additionsServiceInMessengerDelegate = AdditionsServiceInMessenger()
    private val deletionServiceInMessengerDelegate = DeletionServiceInMessenger()
    private val receivingServiceInMessengerDelegate = ReceivingServiceInMessenger()
    private val updateServiceInMessengerDelegate = UpdateServiceInMessenger()

    suspend fun getMessengerByParticipants(participants: List<Long>): ChatData? {
        return receivingServiceInMessengerDelegate.getMessengerByParticipants(participants)
    }

    suspend fun getAllMessengersUsingUserId(userId: Long): List<ChatData> {
        return receivingServiceInMessengerDelegate.getAllMessengersUsingUserId(userId)
    }

    suspend fun addMessenger(messenger: ChatData): ChatData? {
        return additionsServiceInMessengerDelegate.addMessenger(messenger = messenger)
    }
}