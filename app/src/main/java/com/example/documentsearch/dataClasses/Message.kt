package com.example.documentsearch.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val date: String,
    val time: String,
    val message: String,
    val messenger: Long,
    val myMessage: Boolean
)
