package com.ali.marvelapp.data.model.homeModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemXX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?
):Serializable