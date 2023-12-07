package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.ui.theme.cacheAllUsersProfile

class CacheAllUsersProfile {
    fun getAllUsersProfileFromCache(): List<AnotherUserProfilePrototype>? {
        return cacheAllUsersProfile.value.getData()
    }

    fun loadAllUsersProfile(allUsersProfile: List<AnotherUserProfilePrototype>) {
        cacheAllUsersProfile.value.loadData(allUsersProfile)
    }

    fun clearAllUsersProfile() {
        cacheAllUsersProfile.value.clearData()
    }
}