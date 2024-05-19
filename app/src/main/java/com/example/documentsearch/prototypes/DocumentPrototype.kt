package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class DocumentPrototype(
    val id: Long? = null,
    val title: String,
    val category: String,
    val file_id: Long,
    val date: String,
    val image: String? = null,
    val user_id: Long,
    val tags: String,
    val description: String? = null)