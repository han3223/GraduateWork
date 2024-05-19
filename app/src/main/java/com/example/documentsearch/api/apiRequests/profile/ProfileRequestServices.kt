package com.example.documentsearch.api.apiRequests.profile

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileRequestServices {
    @GET("profiles-get/check-by-email")
    suspend fun getProfileUsingEmail(@Query("email") email: String): Response<ResponseBody>

    @GET("profiles-get/check-by-phone-number")
    suspend fun getProfileUsingPhoneNumber(@Query("phoneNumber") phoneNumber: String): Response<ResponseBody>

    @GET("profiles-get/get-by-email-and-password")
    suspend fun getProfileUsingEmailAndPassword(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResponseBody>

    @GET("profiles-get/get-by-personal-name")
    suspend fun getProfileUsingPersonalName(@Query("personalName") personalName: String): Response<ResponseBody>

    @GET("profiles-get/get-all-without-password")
    suspend fun getAllProfileWithoutPassword(): Response<ResponseBody>

    @GET("profiles-get/get-without-password-by-id")
    suspend fun getAnotherProfileUsingId(@Query("id") idProfile: Long): Response<ResponseBody>

    @GET("profiles-get/get-recovery-code-by-last-name-and-phone-number")
    suspend fun getRecoveryCodeUsingLastNameAndPhoneNumber(
        @Query("lastName") lastName: String,
        @Query("phoneNumber") phoneNumber: String,
    ): Response<ResponseBody>

    @GET("profiles-get/get-recovery-code-by-last-name-and-email")
    suspend fun getRecoveryCodeUsingLastNameAndEmail(
        @Query("lastName") lastName: String,
        @Query("email") email: String,
    ): Response<ResponseBody>

    @GET("profiles-get/get-verification-code")
    suspend fun getVerificationCode(
        @Query("email") email: String,
    ): Response<ResponseBody>

    @POST("profiles-post/add")
    suspend fun addProfile(@Body jsonPrototypeProfile: RequestBody): Response<ResponseBody>

    @PUT("profiles-put/update-personal-name")
    suspend fun updatePersonalNameUsingEmail(
        @Query("email") email: String,
        @Query("personalName") personalName: String
    ): Response<ResponseBody>

    @PUT("profiles-put/update-personal-info")
    suspend fun updatePersonalInfoUsingEmail(
        @Query("email") email: String,
        @Query("personalInfo") personalInfo: String
    ): Response<ResponseBody>

    @PUT("profiles-put/update-phone-number")
    suspend fun updateNumberPhoneUsingEmail(
        @Query("email") email: String,
        @Query("phoneNumber") phoneNumber: String
    ): Response<ResponseBody>

    @PUT("profiles-put/update-email")
    suspend fun updateEmailUsingOldEmail(
        @Query("oldEmail") oldEmail: String,
        @Query("newEmail") newEmail: String
    ): Response<ResponseBody>

    @PUT("profiles-put/update-password")
    suspend fun updatePasswordUsingEmail(
        @Query("email") email: String,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Response<ResponseBody>

    @PUT("profiles-put/update-tags")
    suspend fun updateTagsUsingEmail(
        @Query("email") email: String,
        @Query("tags") tags: String,
    ): Response<ResponseBody>

    @PUT("profiles-put/update-password-by-phone-number")
    suspend fun updatePasswordUsingPhoneNumber(
        @Query("phoneNumber") phoneNumber: String,
        @Query("newPassword") newPassword: String,
    ): Response<ResponseBody>

    @PUT("profiles-put/update-password-by-email")
    suspend fun updatePasswordUsingEmail(
        @Query("email") email: String,
        @Query("newPassword") newPassword: String,
    ): Response<ResponseBody>
}