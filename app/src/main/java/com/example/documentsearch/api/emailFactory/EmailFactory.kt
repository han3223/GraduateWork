package com.example.documentsearch.api.emailFactory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class EmailFactory {
    private val fromEmail = "vault.docs@yandex.ru"
    private val secretKey = "qidgkqyiyzvqexjh"

    fun sendEmail(recipient: String): Int {
        val code = (1000..9999).random()

        CoroutineScope(Dispatchers.IO).launch {
            // Настройки SMTP сервера
            val host = "smtp.yandex.ru"
            val port = "587" // порт SMTP сервера (обычно 587 или 465 для SSL)
            val username = "vault.docs@yandex.ru"
            val password = "qidgkqyiyzvqexjh"

            // Настройка свойств
            val props = Properties()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.host"] = host
            props["mail.smtp.port"] = port

            // Создание сессии
            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })

            try {
                // Создание сообщения
                val message = MimeMessage(session)
                message.setFrom(InternetAddress(username))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
                message.subject = "Код"
                message.setText(code.toString())

                // Отправка сообщения
                Transport.send(message)

                println("Письмо успешно отправлено!")
            } catch (e: MessagingException) {
                println("Ошибка при отправке письма: ${e.message}")
            }
        }

        return code
    }
}