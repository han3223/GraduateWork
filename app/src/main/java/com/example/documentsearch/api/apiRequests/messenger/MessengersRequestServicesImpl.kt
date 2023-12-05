package com.example.documentsearch.api.apiRequests.messenger

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.messenger.delete.DeletionServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.get.ReceivingServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.post.AdditionsServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.put.UpdateServiceInMessenger
import com.example.documentsearch.prototypes.AddMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase

class MessengersRequestServicesImpl : ClientAPI() {
    private val additionsServiceInMessengerDelegate = AdditionsServiceInMessenger()
    private val deletionServiceInMessengerDelegate = DeletionServiceInMessenger()
    private val receivingServiceInMessengerDelegate = ReceivingServiceInMessenger()
    private val updateServiceInMessengerDelegate = UpdateServiceInMessenger()

    suspend fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        return receivingServiceInMessengerDelegate.getAllMessengersUsingUserId(userId)
    }

    suspend fun addMessenger(messenger: AddMessengerPrototypeDataBase): GetMessengerPrototypeDataBase? {
        return additionsServiceInMessengerDelegate.addMessenger(messenger)
    }
}