package com.example.testtaskandroid.retrofit

import com.example.testtaskandroid.data.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface JSonPlaceHolderAPI {
    // Characters
    @GET("character")
    fun getCharactersPage(@Query("page") page: Int): Observable<CharacterResponse>

    // Location
    @GET("episode/{ep}")
    fun getEpisodeName(@Path("ep") ep: Int): Observable<EpisodeName>

    @GET("location/{id}")
    fun getLocationById(@Path("id") id: Int): Call<ResultLocation>


}
