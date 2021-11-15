package com.example.testtaskandroid.retrofit


object NetworkService {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    val jSonPlaceHolderAPI: JSonPlaceHolderAPI
        get() = RetrofitClient.getClient(BASE_URL).create(JSonPlaceHolderAPI::class.java)
}
