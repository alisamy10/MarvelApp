package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {



    @GET("/v1/public/characters")
   suspend fun getCharactersFromApi(
        @Query("ts") timeStamp: Long=1
    ): MarvelResponse
}