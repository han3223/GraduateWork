package com.example.documentsearch.api

import android.util.Log
import com.example.documentsearch.prototypes.MessagePrototype
import com.example.documentsearch.screens.messenger.communication.selectedMessenger
import com.example.documentsearch.ui.theme.cacheMessengers
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.serialization.json.Json
import java.net.URISyntaxException


object SocketManager {
    private var socket: Socket? = null

    fun connect() {
        try {
            val options = IO.Options().apply {
                forceNew = true
                reconnection = true
                reconnectionAttempts = Int.MAX_VALUE
                reconnectionDelay = 1000
                reconnectionDelayMax = 5000
            }
            socket = IO.socket("http://192.168.0.6:5000", options)

            socket?.connect()
        } catch (e: URISyntaxException) {
            Log.e("Ошибка в подключении к сокету", "${e.message}")
        }
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun connectCommunicationRoom(idMessenger: Long) {
        if (socket != null) {
            socket!!.emit("join_communication_room", idMessenger)
            Log.i("Подключение к чату", "Подключение к чату выполнено")

            socket?.on("message") { args ->
                val jsonObject = args[0]
                val message = Json.decodeFromString<MessagePrototype>(jsonObject.toString())

                if (selectedMessenger.value?.id!! == message.messenger_id) {
                    val updatedMessagesList = selectedMessenger.value!!.messages.toMutableList()
                    updatedMessagesList.add(message)
                    selectedMessenger.value = selectedMessenger.value!!.copy(messages = updatedMessagesList)
                }

                cacheMessengers.value.first { it.id == message.messenger_id }.messages += message
                Log.i("test", cacheMessengers.value.toString())
            }
        }
    }

    fun sendMessage(message: String) {
        if (socket != null) {
            socket!!.emit("send_message", message)
        }
    }
}



