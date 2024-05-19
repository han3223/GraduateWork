package com.example.documentsearch.api.apiRequests.profile

import com.example.documentsearch.api.ClientAPI
import com.example.documentsearch.api.apiRequests.profile.delete.DeletionServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInAnotherProfile
import com.example.documentsearch.api.apiRequests.profile.get.ReceivingServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.post.AdditionsServiceInProfile
import com.example.documentsearch.api.apiRequests.profile.put.UpdateServiceInProfile
import com.example.documentsearch.prototypes.UserProfilePrototype
import com.example.documentsearch.ui.theme.isProfilesLoad

class ProfileRequestServicesImpl : ClientAPI() {
    private val additionsServiceInProfileDelegate = AdditionsServiceInProfile()
    private val deletionServiceInProfileDelegate = DeletionServiceInProfile()
    private val receivingServiceInProfileDelegate = ReceivingServiceInProfile()
    private val receivingServiceInAnotherProfileDelegate = ReceivingServiceInAnotherProfile()
    private val updateServiceInProfileDelegate = UpdateServiceInProfile()

    suspend fun addProfile(profilePrototype: UserProfilePrototype): UserProfilePrototype? {
        return additionsServiceInProfileDelegate.addProfile(profile = profilePrototype)
    }


    suspend fun getAllUsersProfile(): List<UserProfilePrototype> {
        val result = receivingServiceInAnotherProfileDelegate.getAllAnotherProfile()
        isProfilesLoad.value = true
        return result
    }

    suspend fun getAnotherProfileUsingId(idProfile: Long): UserProfilePrototype? {
        return receivingServiceInAnotherProfileDelegate.getAnotherProfileUsingId(idProfile = idProfile)
    }


    suspend fun getProfileUsingEmail(email: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmail(email = email)
    }

    suspend fun getProfileUsingPhoneNumber(phoneNumber: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPhoneNumber(phoneNumber = phoneNumber)
    }

    suspend fun getProfileUsingPersonalName(personalName: String): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingPersonalName(personalName = personalName)
    }

    suspend fun getProfileUsingEmailAndPassword(
        email: String,
        password: String
    ): UserProfilePrototype? {
        return receivingServiceInProfileDelegate.getProfileUsingEmailAndPassword(
            email = email,
            password = password
        )
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndEmail(lastName: String, email: String): Int? {
        return receivingServiceInProfileDelegate.getRecoveryCodeUsingLastNameAndEmail(
            lastName = lastName,
            email = email
        )
    }

    suspend fun getVerificationCode(email: String): String {
        return receivingServiceInProfileDelegate.getVerificationCode(email)
    }

    suspend fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(
        lastName: String,
        phoneNumber: String
    ): Int? {
        return receivingServiceInProfileDelegate.getRecoveryCodeUsingLastNameAndPhoneNumber(
            lastName = lastName,
            phoneNumber = phoneNumber
        )
    }


    suspend fun updatePersonalNameUsingEmail(email: String, personalName: String): Boolean {
        return updateServiceInProfileDelegate.updatePersonalNameUsingEmail(
            email = email,
            personalName = personalName
        )
    }

    suspend fun updatePersonalInfoUsingEmail(email: String, personalInfo: String): Boolean {
        return updateServiceInProfileDelegate.updatePersonalInfoUsingEmail(
            email = email,
            personalInfo = personalInfo
        )
    }

    suspend fun updateNumberPhoneUsingEmail(email: String, phoneNumber: String): Boolean {
        return updateServiceInProfileDelegate.updateNumberPhoneUsingEmail(
            email = email,
            phoneNumber = phoneNumber
        )
    }

    suspend fun updateEmailUsingOldEmail(oldEmail: String, newEmail: String): Boolean {
        return updateServiceInProfileDelegate.updateEmailUsingOldEmail(
            oldEmail = oldEmail,
            newEmail = newEmail
        )
    }

    suspend fun updatePasswordUsingEmail(
        email: String,
        oldPassword: String,
        newPassword: String
    ): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(
            email = email,
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

    suspend fun updateTagsUsingEmail(email: String, tags: String): Boolean {
        return updateServiceInProfileDelegate.updateTagsUsingEmail(email = email, tags = tags)
    }

    suspend fun updatePasswordUsingPhoneNumber(phoneNumber: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingPhoneNumber(
            phoneNumber = phoneNumber,
            newPassword = newPassword
        )
    }

    suspend fun updatePasswordUsingEmail(email: String, newPassword: String): Boolean {
        return updateServiceInProfileDelegate.updatePasswordUsingEmail(
            email = email,
            newPassword = newPassword
        )
    }
}