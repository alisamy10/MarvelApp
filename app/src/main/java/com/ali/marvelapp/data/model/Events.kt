package com.ali.marvelapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Events(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<ItemX>?,
    @SerializedName("returned")
    val returned: Int?
): Serializable