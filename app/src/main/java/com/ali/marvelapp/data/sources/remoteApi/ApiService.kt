package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
   suspend fun getCharactersFromApi(@Query("ts") timeStamp: Int=1 ,
                                    @Query("nameStartsWith") name:String?=null ,
                                    @Query("offset")offset:Int) : Response<MarvelResponse>


}