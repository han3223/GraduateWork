package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class DocumentPrototype(
    val id: Long? = null,
    val title: String,
    val category: String,
    val document: ByteArray,
    val date: String,
    val image: String? = null,
    val user: Long,
    val tags: List<String>,
    val description: String? = null)

@Serializable
data class AddDocumentPrototype(
    val id: Long? = null,
    val title: String,
    val category: String,
    val document: ByteArray,
    val date: String,
    val image: String? = null,
    val user: Long,
    val tags: List<String>,
    val description: String)