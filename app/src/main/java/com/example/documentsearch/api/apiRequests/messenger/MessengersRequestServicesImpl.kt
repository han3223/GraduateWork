package com.example.documentsearch.api.apiRequests.messenger

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.message.MessageRequestServicesImpl
import com.example.documentsearch.api.apiRequests.messenger.delete.DeletionServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.get.ReceivingServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.post.AdditionsServiceInMessenger
import com.example.documentsearch.api.apiRequests.messenger.put.UpdateServiceInMessenger
import com.example.documentsearch.api.apiRequests.profile.ProfileRequestServicesImpl
import com.example.documentsearch.prototypes.AddMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.GetMessengerPrototypeDataBase
import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.prototypes.UserProfilePrototype

class MessengersRequestServicesImpl : ClientAPI() {
    private val additionsServiceInMessengerDelegate = AdditionsServiceInMessenger()
    private val deletionServiceInMessengerDelegate = DeletionServiceInMessenger()
    private val receivingServiceInMessengerDelegate = ReceivingServiceInMessenger()
    private val updateServiceInMessengerDelegate = UpdateServiceInMessenger()

    private suspend fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        return receivingServiceInMessengerDelegate.getAllMessengersUsingUserId(userId = userId)
    }

    suspend fun addMessenger(messenger: AddMessengerPrototypeDataBase): GetMessengerPrototypeDataBase? {
        return additionsServiceInMessengerDelegate.addMessenger(messenger = messenger)
    }

    suspend fun getPrototypeMessengers(userProfile: UserProfilePrototype): MutableList<MessengerPrototype> {
        val userMessengers = mutableListOf<MessengerPrototype>()
        val messengerPrototypeDataBase = getAllMessengersUsingUserId(userId = userProfile.id!!)
        messengerPrototypeDataBase?.forEach { messenger ->
            val anotherUser =
                ProfileRequestServicesImpl().getAnotherProfileUsingId(idProfile = messenger.interlocutor)
            if (anotherUser != null) {
                val messages = MessageRequestServicesImpl().getMessagesFromMessenger(idMessenger = messenger.id)
                val messengerPrototype =
                    MessengerPrototype(
                        id = messenger.id,
                        interlocutor = anotherUser,
                        listMessage = messages
                    )
                userMessengers.add(element = messengerPrototype)
            }
        }

        return userMessengers
    }
}