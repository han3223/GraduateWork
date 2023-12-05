package com.example.documentsearch.api.apiRequests.profile

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.profile.delete.DeletionServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInAnotherProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.post.AdditionsServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.put.UpdateServiceInProfile
import com.example.documentsearch.prototypes.AnotherUserProfilePrototype
import com.example.documentsearch.prototypes.UserProfilePrototype

class ProfileRequestServicesImpl : ClientAPI() {
    private val additionsServiceInProfileDelegate = AdditionsServiceInProfile()
    private val deletionServiceInProfileDelegate = DeletionServiceInProfile()
    private val receivingServiceInProfileDelegate = ReceivingServiceInProfile()
    private val receivingServiceInAnotherProfileDelegate = ReceivingServiceInAnotherProfile()
    private val updateServiceInProfileDelegate = UpdateServiceInProfile()

    suspend fun addProfile(profilePrototype: UserProfilePrototype): UserProfilePrototype? {
        return additionsServiceInProfileDelegate.addProfile(profilePrototype)
    }


    suspend fun getAllAnotherProfile(): List<AnotherUserProfilePrototype> {
        return receivingServiceInAnotherProfileDelegate.getAllAnotherProfile()
    }

    suspend fun getAnotherProfileUsingId(idProfile: Long): AnotherUserProfilePrototype? {
        return receivingServiceInAnotherProfileDelegate.getAnotherProfileUsingId(idProfile)
    }


    suspend fun getProfileUsingEmail(email: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmail(email)
    }

    suspend fun getProfileUsingPhoneNumber(phoneNumber: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPhoneNumber(phoneNumber)
    }

    suspend fun getProfileUsingPersonalName(personalName: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPersonalName(personalName)
    }

    suspend fun getProfileUsingEmailAndPassword(email: String, password: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmailAndPassword(email, password)
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        return receivingServiceInProfileDelegate.getProfileRecoveryCodeUsingLastNameAndEmail(lastName, email)
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName: String, phoneNumber: String): Int? {
        return receivingServiceInProfileDelegate.getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName, phoneNumber)
    }


    suspend fun updatePersonalNameUsingEmail(email: String, personalName: String): String? {
        return updateServiceInProfileDelegate.updatePersonalNameUsingEmail(email, personalName)
    }

    suspend fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): String? {
        return updateServiceInProfileDelegate.updatePersonalInfoUsingEmail(email, personalInfo)
    }

    suspend fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): String? {
        return updateServiceInProfileDelegate.updateNumberPhoneUsingEmail(email, phoneNumber)
    }

    suspend fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): String? {
        return updateServiceInProfileDelegate.updateEmailUsingOldEmail(oldEmail, newEmail)
    }

    suspend fun updatePasswordUsingEmail(email: String, oldPassword: String, newPassword: String): String? {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(email, oldPassword, newPassword)
    }

    suspend fun updateTagsUsingEmail(email: String, tags: String): Boolean {

        return updateServiceInProfileDelegate.updateTagsUsingEmail(email, tags)
    }

    suspend fun addTagUsingEmail(email: String, tag: String): Boolean {
        return updateServiceInProfileDelegate.addTagUsingEmail(email, tag)
    }

    suspend fun deleteTagUsingEmail(email: String, tag: String): Boolean {
        return updateServiceInProfileDelegate.deleteTagUsingEmail(email, tag)
    }

    suspend fun addFriendUsingEmail(email: String, friend: String): Boolean {
        return updateServiceInProfileDelegate.addFriendUsingEmail(email, friend)
    }

    suspend fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingPhoneNumber(phoneNumber, newPassword)
    }

    suspend fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(email, newPassword)
    }
}