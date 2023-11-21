package com.example.documentsearch.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Messenger(val id: Long? = null, val interlocutor: AnotherUser, val listMessage: MutableList<Message>)

@Serializable
data class AddMessenger(val id: Long? = null, val user: Long, val interlocutor: Long)

@Serializable
data class GetMessenger(val id: Long, val user: Long, val interlocutor: Long)
