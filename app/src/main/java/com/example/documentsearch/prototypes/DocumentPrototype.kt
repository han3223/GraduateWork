package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class DocumentPrototype(
    val id: Long,
    val title: String,
    val category: String,
    val document: String,
    val date: String,
    val image: String?,
    val user: Long,
    val tags: List<String>,
    val percent: Float,
    val description: String)