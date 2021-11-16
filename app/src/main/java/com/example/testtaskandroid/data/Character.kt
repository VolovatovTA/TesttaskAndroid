package com.example.testtaskandroid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("species")
    @Expose
    val species: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("origin")
    @Expose
    val origin: Location,
    @SerializedName("location")
    @Expose
    val location: Location,
    @SerializedName("image")
    @Expose
    val image: String,
    @SerializedName("episode")
    @Expose
    val episode: List<String>,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("created")
    @Expose
    val created: String,
    var nameOfFirstEpisode: String
)