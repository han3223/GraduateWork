@file:Suppress("DEPRECATION")

package com.example.documentsearch.preferences

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

class PreferencesManager(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveData(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun removeData(key: String) {
        sharedPreferences.edit { remove(key) }
    }
}