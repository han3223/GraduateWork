package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable


enum class StatusMessage(val status: String) {
    WAIT("1"),
    OK("0"),
    ERROR("-1")
}

@Serializable
data class ChatData(
    val id: Long? = null,
    val participants: List<UserProfilePrototype>,
    var messages: List<MessagePrototype> = listOf(),
    val status: String? = StatusMessage.WAIT.status
)
