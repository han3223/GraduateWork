package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePrototype(
    var id: Long? = null,
    var lastName: String,
    var firstName: String,
    var patronymic: String,
    var telephoneNumber: String,
    var email: String,
    var password: String,
    var image: String? = null,
    var tags: List<Long>? = null,
    var personalName: String? = null,
    var personalInfo: String? = null,
    var friends: List<Long>? = null)