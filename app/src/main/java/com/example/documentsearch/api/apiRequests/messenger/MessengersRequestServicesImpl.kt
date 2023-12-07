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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MessengersRequestServicesImpl : ClientAPI() {
    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val additionsServiceInMessengerDelegate = AdditionsServiceInMessenger()
    private val deletionServiceInMessengerDelegate = DeletionServiceInMessenger()
    private val receivingServiceInMessengerDelegate = ReceivingServiceInMessenger()
    private val updateServiceInMessengerDelegate = UpdateServiceInMessenger()

    private suspend fun getAllMessengersUsingUserId(userId: Long): MutableList<GetMessengerPrototypeDataBase>? {
        return receivingServiceInMessengerDelegate.getAllMessengersUsingUserId(userId)
    }

    suspend fun addMessenger(messenger: AddMessengerPrototypeDataBase): GetMessengerPrototypeDataBase? {
        return additionsServiceInMessengerDelegate.addMessenger(messenger)
    }

    suspend fun getMessengersPrototype(userProfile: UserProfilePrototype): MutableList<MessengerPrototype> {
        val userMessengers = mutableListOf<MessengerPrototype>()
        coroutine.async {
            val messengerPrototypeDataBase = getAllMessengersUsingUserId(userProfile.id!!)
            messengerPrototypeDataBase?.forEach { messenger ->
                val anotherUser =
                    ProfileRequestServicesImpl().getAnotherProfileUsingId(messenger.interlocutor)
                if (anotherUser != null) {
                    val messages = MessageRequestServicesImpl().getMessagesFromMessenger(messenger.id)
                    val messengerPrototype =
                        MessengerPrototype(messenger.id, anotherUser, messages)
                    userMessengers.add(messengerPrototype)
                }
            }
        }.await()

        return userMessengers
    }
}