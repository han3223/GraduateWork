package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class TagPrototype(val id: Long? = null, val title: String, val type: String)