package com.example.documentsearch.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class AnotherUser(
    val id: Long,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    var personalName: String?,
    var personalInfo: String?,
    var numberPhone: String,
    var email: String,
    val image: String? = null,
    val tags: List<Long>? = null,
)
