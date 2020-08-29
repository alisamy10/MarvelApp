package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse
import retrofit2.Response

interface IApiHelper {


    suspend fun getCharacters(name: String?,offset:Int): Response<MarvelResponse>


}