package com.ali.marvelapp.data.model.detailsModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("path")
    val path: String?
): Serializable