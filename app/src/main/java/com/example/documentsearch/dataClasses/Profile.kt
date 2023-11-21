package com.example.documentsearch.dataClasses

import kotlinx.serialization.Serializable

//data class Profile(
//    val fullName: String,
//    var personalName: String,
//    var personalInfo: String,
//    var numberPhone: String,
//    var email: String,
//    var password: String
//)

@Serializable
data class Profile(
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


@Serializable
data class ProfileRegistration(
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val telephoneNumber: String,
    val email: String,
    val password: String,
)