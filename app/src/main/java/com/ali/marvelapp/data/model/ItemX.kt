package com.ali.marvelapp.data.model


import com.google.gson.annotations.SerializedName

data class ItemX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?
)