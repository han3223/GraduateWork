package com.example.documentsearch.patterns

class EditText {
    fun getMaskNumberPhone(numberPhone: String): String {
        var maskedNumber = ""
        numberPhone.forEachIndexed { index, char ->
            when (index) {
                0 -> maskedNumber = "+$char"
                1 -> maskedNumber += " ($char"
                4 -> maskedNumber += ") $char"
                7, 9 -> maskedNumber += "-$char"
                else -> maskedNumber += char
            }
        }

        return maskedNumber
    }
}