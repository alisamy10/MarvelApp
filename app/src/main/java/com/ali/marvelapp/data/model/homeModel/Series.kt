package com.ali.marvelapp.data.model.homeModel


import com.ali.marvelapp.data.model.homeModel.ItemXX
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Series(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<ItemXX>?,
    @SerializedName("returned")
    val returned: Int?
): Serializable