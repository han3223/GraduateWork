package com.example.documentsearch.Cache

import com.example.documentsearch.prototypes.MessengerPrototype
import com.example.documentsearch.ui.theme.cacheMessengers

class CacheUserMessengers {
    fun getMessengersFromCache(): List<MessengerPrototype>? {
        return cacheMessengers.value.getData()
    }

    fun loadMessengers(messengers: List<MessengerPrototype>) {
        cacheMessengers.value.loadData(messengers)
    }

    fun clearMessengers() {
        cacheMessengers.value.clearData()
    }
}