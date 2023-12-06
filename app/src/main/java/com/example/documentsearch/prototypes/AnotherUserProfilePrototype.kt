package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class AnotherUserProfilePrototype(
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
