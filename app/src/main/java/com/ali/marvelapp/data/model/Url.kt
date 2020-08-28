package com.ali.marvelapp.data.model


import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)