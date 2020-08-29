package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.model.MarvelResponse
import retrofit2.Response


interface IOnlineDataSource {


   suspend fun getCharactersByName(name: String) : Data

   suspend fun getCharacters(offset: Int) : Response<MarvelResponse>

}