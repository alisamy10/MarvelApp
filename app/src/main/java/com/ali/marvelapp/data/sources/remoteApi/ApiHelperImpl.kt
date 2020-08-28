package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor (private val apiService: ApiService) : IApiHelper {

    override suspend fun getCharacters(): MarvelResponse =apiService.getCharactersFromApi()


}
