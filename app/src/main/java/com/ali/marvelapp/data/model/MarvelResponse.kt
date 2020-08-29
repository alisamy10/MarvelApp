package com.ali.marvelapp.data.model


import com.ali.marvelapp.data.model.Data
import com.google.gson.annotations.SerializedName

data class MarvelResponse(
    @SerializedName("attributionHTML")
    val attributionHTML: String?=null,
    @SerializedName("attributionText")
    val attributionText: String?=null,
    @SerializedName("code")
    val code: Int?=0,
    @SerializedName("copyright")
    val copyright: String?=null,
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("etag")
    val etag: String? =null,
    @SerializedName("status")
    val status: String? =null
)