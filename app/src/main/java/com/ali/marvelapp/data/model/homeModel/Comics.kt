package com.ali.marvelapp.data.model.homeModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comics (
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("returned")
    val returned: Int?
):Serializable