package com.ali.marvelapp.data.model.homeModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(
    @SerializedName("title")
    @Expose
    private var title: String? = null,
    @SerializedName("comics")
    val comics: Comics?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("events")
    val events: Events?=null,
    @SerializedName("id")
    val id: Int?=0,
    @SerializedName("modified")
    val modified: String?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("resourceURI")
    val resourceURI: String?=null,
    @SerializedName("series")
    val series: Series?=null,
    @SerializedName("stories")
    val stories: Stories?=null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?=null,
    @SerializedName("urls")
    val urls: List<Url>?=null
):Serializable