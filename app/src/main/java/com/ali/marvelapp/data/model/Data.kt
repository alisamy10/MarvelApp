package com.ali.marvelapp.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int?=0,
    @SerializedName("limit")
    val limit: Int?=0,
    @SerializedName("offset")
    val offset: Int?=0,
    @SerializedName("results")
    val results: List<Result>?=null,
    @SerializedName("total")
    val total: Int?=0
)