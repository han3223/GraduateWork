package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.cacheUserProfile

class CacheUserProfile {
    fun getUserFromCache(): UserProfilePrototype? {
        return cacheUserProfile.value.getData()
    }

    fun loadUser(user: UserProfilePrototype) {
        cacheUserProfile.value.loadData(user)
    }

    fun clearData() {
        cacheUserProfile.value.clearData()
    }
}