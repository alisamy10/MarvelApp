package com.ali.marvelapp.data.model.detailsModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stories(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<ItemXXX>?,
    @SerializedName("returned")
    val returned: Int?
): Serializable