package com.example.documentsearch.api.apiRequests.profile

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileRequestServices {
    @POST("requests/profile/addProfile")
    suspend fun addProfile(@Body jsonPrototypeProfile: RequestBody): Response<ResponseBody>

    @GET("requests/profile/getAllProfileWithoutPassword")
    suspend fun getAllProfileWithoutPassword(): Response<ResponseBody>

    @GET("requests/profile/getProfileByEmailAndPassword")
    suspend fun getProfileUsingEmailAndPassword(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResponseBody>

    @GET("requests/profile/getAnotherProfileById")
    suspend fun getAnotherProfileUsingId(@Query("id") idProfile: Long): Response<ResponseBody>

    @GET("requests/profile/getProfileByEmail")
    suspend fun getProfileUsingEmail(@Query("email") email: String): Response<ResponseBody>

    @GET("requests/profile/getProfileByTelephoneNumber")
    suspend fun getProfileUsingPhoneNumber(@Query("telephoneNumber") phoneNumber: String): Response<ResponseBody>

    @GET("requests/profile/getProfileByPersonalName")
    suspend fun getProfileUsingPersonalName(@Query("personalName") personalName: String): Response<ResponseBody>

    @GET("requests/profile/getProfileRecoveryCodeUsingLastNameAndPhoneNumber")
    suspend fun getProfileRecoveryCodeUsingLastNameAndPhoneNumber(
        @Query("lastName") lastName: String,
        @Query("telephoneNumber") phoneNumber: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileRecoveryCodeUsingLastNameAndEmail")
    suspend fun getProfileRecoveryCodeUsingLastNameAndEmail(
        @Query("lastName") lastName: String,
        @Query("email") email: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePersonalName")
    suspend fun updatePersonalNameUsingEmail(
        @Query("email") email: String,
        @Query("personalName") personalName: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePersonalInfo")
    suspend fun updatePersonalInfoUsingEmail(
        @Query("email") email: String,
        @Query("personalInfo") personalInfo: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateNumberPhone")
    suspend fun updateNumberPhoneUsingEmail(
        @Query("email") email: String,
        @Query("telephoneNumber") phoneNumber: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateEmail")
    suspend fun updateEmailUsingOldEmail(
        @Query("oldEmail") oldEmail: String,
        @Query("newEmail") newEmail: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePassword")
    suspend fun updatePasswordUsingEmail(
        @Query("email") email: String,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateTags")
    suspend fun updateTagsUsingEmail(
        @Query("email") email: String,
        @Query("tags") tags: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/addTag")
    suspend fun addTagUsingEmail(
        @Query("email") email: String,
        @Query("tag") tag: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/deleteTag")
    suspend fun deleteTagUsingEmail(
        @Query("email") email: String,
        @Query("tag") tag: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/forgotPasword/updatePasswordUsingTelephoneNumber")
    suspend fun updatePasswordUsingPhoneNumber(
        @Query("telephoneNumber") phoneNumber: String,
        @Query("newPassword") newPassword: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/forgotPasword/updatePasswordUsingEmail")
    suspend fun updatePasswordUsingEmail(
        @Query("email") email: String,
        @Query("newPassword") newPassword: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/addFriend")
    suspend fun addFriendUsingEmail(
        @Query("email") email: String,
        @Query("friends") friend: String,
    ): Response<ResponseBody>
}