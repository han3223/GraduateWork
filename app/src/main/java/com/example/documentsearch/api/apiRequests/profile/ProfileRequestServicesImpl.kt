package com.example.documentsearch.api.apiRequests.profile

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.profile.delete.DeletionServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInAnotherProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.post.AdditionsServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.put.UpdateServiceInProfile
import com.example.documentsearch.prototypes.AnotherUserPrototype
import com.example.documentsearch.prototypes.ProfilePrototype

class ProfileRequestServicesImpl: ClientAPI() {
    private val additionsServiceInProfileDelegate = AdditionsServiceInProfile()
    private val deletionServiceInProfileDelegate = DeletionServiceInProfile()
    private val receivingServiceInProfileDelegate = ReceivingServiceInProfile()
    private val receivingServiceInAnotherProfileDelegate = ReceivingServiceInAnotherProfile()
    private val updateServiceInProfileDelegate = UpdateServiceInProfile()

    fun addProfile(profile: ProfilePrototype): ProfilePrototype? {
        return additionsServiceInProfileDelegate.addProfile(profile)
    }


    fun getAllAnotherProfile(): List<AnotherUserPrototype> {
        return receivingServiceInAnotherProfileDelegate.getAllAnotherProfile()
    }

    fun getAnotherProfileUsingId(idProfile: Long): AnotherUserPrototype? {
        return receivingServiceInAnotherProfileDelegate.getAnotherProfileUsingId(idProfile)
    }


    fun getProfileUsingEmail(email: String): ProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmail(email)
    }

    fun getProfileUsingPhoneNumber(phoneNumber: String): ProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPhoneNumber(phoneNumber)
    }

    fun getProfileUsingPersonalName(personalName: String): ProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPersonalName(personalName)
    }

    fun getProfileUsingEmailAndPassword(email: String, password: String): ProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmailAndPassword(email, password)
    }

    fun getProfileRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        return receivingServiceInProfileDelegate.getProfileRecoveryCodeUsingLastNameAndEmail(lastName, email)
    }

    fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName: String, phoneNumber: String): Int? {
        return receivingServiceInProfileDelegate.getProfileRecoveryCodeUsingLastNameAndPhoneNumber(lastName, phoneNumber)
    }


    fun updatePersonalNameUsingEmail(email: String, personalName: String): String? {
        return updateServiceInProfileDelegate.updatePersonalNameUsingEmail(email, personalName)
    }

    fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): String? {
        return updateServiceInProfileDelegate.updatePersonalInfoUsingEmail(email, personalInfo)
    }

    fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): String? {
        return updateServiceInProfileDelegate.updateNumberPhoneUsingEmail(email, phoneNumber)
    }

    fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): String? {
        return updateServiceInProfileDelegate.updateEmailUsingOldEmail(oldEmail, newEmail)
    }

    fun updatePasswordUsingEmail(email: String, oldPassword: String, newPassword: String): String? {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(email, oldPassword, newPassword)
    }

    fun updateTagsUsingEmail(email: String, tags: String): Boolean {
        return updateServiceInProfileDelegate.updateTagsUsingEmail(email, tags)
    }

    fun addTagUsingEmail(email: String, tag: String): Boolean {
        return updateServiceInProfileDelegate.addTagUsingEmail(email, tag)
    }

    fun deleteTagUsingEmail(email: String, tag: String): Boolean {
        return updateServiceInProfileDelegate.deleteTagUsingEmail(email, tag)
    }

    fun addFriendUsingEmail(email: String, friend: String): Boolean {
        return updateServiceInProfileDelegate.addFriendUsingEmail(email, friend)
    }

    fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingPhoneNumber(phoneNumber, newPassword)
    }

    fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(email, newPassword)
    }
}