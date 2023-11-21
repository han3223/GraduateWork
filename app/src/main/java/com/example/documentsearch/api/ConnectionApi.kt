package com.example.documentsearch.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionApi {
    val RETROFIT: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.10.10.13:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}