package com.example.documentsearch.cache

import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.ui.theme.cacheAllUsersProfile

class CacheAllUsersProfile {
    fun getAllUserProfileFromCache(): List<AnotherUserProfilePrototype>? {
        return cacheAllUsersProfile.value.getData()
    }

    fun loadAllUserProfile(allUserProfile: List<AnotherUserProfilePrototype>) {
        cacheAllUsersProfile.value.loadData(allUserProfile)
    }

    fun clearAllUserProfile() {
        cacheAllUsersProfile.value.clearData()
    }
}