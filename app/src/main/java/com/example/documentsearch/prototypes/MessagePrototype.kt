package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class MessagePrototype(
    val date: String,
    val time: String,
    val message: String,
    val messenger: Long,
    val myMessage: Boolean
)
