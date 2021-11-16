package com.example.testtaskandroid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EpisodeName(
    @SerializedName("name")
    @Expose
    var name: String
)