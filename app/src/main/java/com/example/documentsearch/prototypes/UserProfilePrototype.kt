package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class UserProfilePrototype(
    var id: Long? = null,
    var lastName: String,
    var firstName: String,
    var patronymic: String,
    var phoneNumber: String,
    var email: String,
    var password: String? = null,
    var image: String? = null,
    var tags: String? = null,
    var personalName: String? = null,
    var personalInfo: String? = null,
    var friends: List<Long>? = null)