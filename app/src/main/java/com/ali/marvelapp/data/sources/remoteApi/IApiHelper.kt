package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse

interface IApiHelper {

    suspend fun getCharacters(): MarvelResponse

}