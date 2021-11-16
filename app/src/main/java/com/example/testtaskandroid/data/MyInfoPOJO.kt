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


