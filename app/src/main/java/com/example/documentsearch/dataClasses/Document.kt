package com.example.documentsearch.dataClasses

import kotlinx.serialization.Serializable

/**
 * Форма документа почученного путём запроса
 */
@Serializable
data class Document(
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

/**
 * Форма с документом и процентом соответствия с вложенным документом
 */
data class DocumentWithPercentage(val document: Document, val percentImage: Int)