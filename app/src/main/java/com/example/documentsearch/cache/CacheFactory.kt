package com.example.documentsearch.cache

import androidx.lifecycle.MutableLiveData

open class CacheFactory<T> {
    private val dataCache = MutableLiveData<T>()

    fun getData(): T? {
        return dataCache.value
    }

    fun clearData() {
        dataCache.value = null
    }

    fun loadData(newData: T?) {
        dataCache.postValue(newData)
    }
}