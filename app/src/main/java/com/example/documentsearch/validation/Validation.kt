package com.example.documentsearch.validation

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


// Класс предназначен для валидации строк
class Validation {

    fun isLowerCase(text: String): Boolean {
        return !Regex("""[a-z]""").containsMatchIn(text)
    }

    fun isUpperCase(text: String): Boolean {
        return !Regex("""[A-Z]""").containsMatchIn(text)
    }

    fun isDigit(text: String): Boolean {
        return !Regex("""[0-9]""").containsMatchIn(text)
    }

    fun isSpecialCharacter(text: String): Boolean {
        return !Regex("""[@#$%^&+-=]""").containsMatchIn(text)
    }

    fun isWhitespace(text: String): Boolean {
        return Regex(""" """).containsMatchIn(text)
    }

    fun isMinLenght(text: String): Boolean {
        val pattern: Pattern
        val passwordPattern = "^.{8,}$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(text)
        return !matcher.matches()
    }

    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(numberPhone: String): Boolean {
        return Regex("\\d+").matches(numberPhone)
    }
}