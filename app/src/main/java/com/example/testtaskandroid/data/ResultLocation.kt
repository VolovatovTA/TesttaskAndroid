package com.example.testtaskandroid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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