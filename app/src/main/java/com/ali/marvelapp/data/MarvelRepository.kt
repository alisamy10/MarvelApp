package com.ali.marvelapp.data


import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.model.MarvelResponse
import com.ali.marvelapp.data.sources.remoteApi.IOnlineDataSource
import retrofit2.Response
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val onlineDataSource: IOnlineDataSource ) {



    suspend fun getChars(offset: Int): Response<MarvelResponse> {
        return onlineDataSource.getCharacters(offset)
    }


    suspend fun searchByName(name: String):Data {
        return onlineDataSource.getCharactersByName(name)

    }


}