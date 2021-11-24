package com.example.testtaskandroid.retrofit

import com.example.testtaskandroid.data.*
import dagger.Binds
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
interface JSonPlaceHolderAPI {
    @GET("character")
    fun getCharactersPage(@Query("page") page: Int): Observable<CharacterResponse>

    @GET("episode/{ep}")
    fun getEpisodeName(@Path("ep") ep: Int): Observable<EpisodeName>

    @GET("location/{id}")
    fun getLocationById(@Path("id") id: Int): Observable<ResultLocation>
}
