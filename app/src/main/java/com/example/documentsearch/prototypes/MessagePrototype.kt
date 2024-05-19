package com.example.documentsearch.prototypes

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class MessagePrototype(
    val id: Long? = null,
    val date: String,
    val time: String,
    val message: String,
    val messenger_id: Long,
    val user_id: Long
) {
    companion object {
        fun fromJsonObject(json: JSONObject): MessagePrototype {
            return MessagePrototype(
                id = json.optLong("id"),
                date = json.getString("date"),
                time = json.getString("time"),
                message = json.getString("message"),
                messenger_id = json.getLong("messenger_id"),
                user_id = json.getLong("user_id")
            )
        }
    }
}
