package com.example.testtaskandroid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharacterResponse  (
    @SerializedName("info")
    @Expose
    var infoPOJO: MyInfoPOJO,
    @SerializedName("results")
    @Expose
    val results: List<Character>,
    var firstEpisodeName: EpisodeName
    )