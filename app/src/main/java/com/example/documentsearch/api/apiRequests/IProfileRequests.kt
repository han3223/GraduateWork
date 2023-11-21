package com.example.documentsearch.api.apiRequests

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface IProfileRequests {
    @POST("requests/profile/addProfile")
    suspend fun addProfile(@Body jsonProfile: RequestBody): Response<ResponseBody>

    @GET("requests/profile/getProfileByEmailAndPassword")
    suspend fun getProfile(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResponseBody>

    @GET("requests/profile/getAllProfileWithoutPassword")
    suspend fun getAllProfileWithoutPassword(): Response<ResponseBody>

    @GET("requests/profile/getAnotherProfileById")
    suspend fun getAnotherProfileById(
        @Query("id") id: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileByEmail")
    suspend fun getProfileByEmail(
        @Query("email") email: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileByTelephoneNumber")
    suspend fun getProfileByPhoneNumber(
        @Query("telephoneNumber") phoneNumber: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileByPersonalName")
    suspend fun getProfileByPersonalName(
        @Query("personalName") personalName: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileByFullNameAndTelephoneNumber")
    suspend fun getProfileByFullNameAndPhoneNumber(
        @Query("lastName") lastName: String,
        @Query("telephoneNumber") phoneNumber: String,
    ): Response<ResponseBody>

    @GET("requests/profile/getProfileByFullNameAndEmail")
    suspend fun getProfileByFullNameAndEmail(
        @Query("lastName") lastName: String,
        @Query("email") email: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePersonalName")
    suspend fun updatePersonalName(
        @Query("email") email: String,
        @Query("personalName") personalName: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePersonalInfo")
    suspend fun updatePersonalInfo(
        @Query("email") email: String,
        @Query("personalInfo") personalInfo: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateNumberPhone")
    suspend fun updateNumberPhone(
        @Query("email") email: String,
        @Query("telephoneNumber") phoneNumber: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateEmail")
    suspend fun updateEmail(
        @Query("oldEmail") oldEmail: String,
        @Query("newEmail") newEmail: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updatePassword")
    suspend fun updatePassword(
        @Query("email") email: String,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Response<ResponseBody>

    @PUT("requests/profile/updateTags")
    suspend fun updateTags(
        @Query("email") email: String,
        @Query("tags") tags: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/addTag")
    suspend fun addTag(
        @Query("email") email: String,
        @Query("tag") tag: String,
    ): Response<ResponseBody>

    @PUT("requests/profile/deleteTag")
    suspend fun deleteTag(
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
    suspend fun addFriend(
        @Query("email") email: String,
        @Query("friends") friend: String,
    ): Response<ResponseBody>

}