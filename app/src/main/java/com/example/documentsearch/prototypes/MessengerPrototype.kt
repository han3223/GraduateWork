package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class MessengerPrototype(
    val id: Long? = null,
    val interlocutor: AnotherUserProfilePrototype,
    val listMessage: MutableList<MessagePrototype>
)

@Serializable
data class AddMessengerPrototypeDataBase(val id: Long? = null, val user: Long, val interlocutor: Long)

@Serializable
data class GetMessengerPrototypeDataBase(val id: Long, val user: Long, val interlocutor: Long)
