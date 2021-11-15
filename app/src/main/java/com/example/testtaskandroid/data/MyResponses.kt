package com.example.testtaskandroid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MyInfoPOJO  (
    @SerializedName("count")
    @Expose
    var count: Int,
    @SerializedName("pages")
    @Expose
    var pages: Int?,
    @SerializedName("next")
    @Expose
    var nextUrl: String,
    @SerializedName("prev")
    @Expose
    var previousUrl: String
)

data class ResultLocation  (
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("type")
    @Expose
    var type: String,
    @SerializedName("dimension")
    @Expose
    var dimension: String,
    @SerializedName("residents")
    @Expose
    var residents: List<String>

)
data class EpisodeName(
    @SerializedName("name")
    @Expose
    var name: String
)

data class CharacterResponse  (
    @SerializedName("info")
    @Expose
    var infoPOJO: MyInfoPOJO,
    @SerializedName("results")
    @Expose
    val results: List<Character>,
    var firstEpisodeName: EpisodeName
    )

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
    val created: String
)

data class Location (
    val name: String,
    val url: String
)


