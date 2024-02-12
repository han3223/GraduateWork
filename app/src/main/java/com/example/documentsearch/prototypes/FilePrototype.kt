package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable

@Serializable
data class FilePrototype(val id: Long? = null, val file: ByteArray)