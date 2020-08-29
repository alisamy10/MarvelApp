package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.MarvelResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor (private val apiService: ApiService) : IApiHelper {
    override suspend fun getCharacters(name: String?, offset: Int): Response<MarvelResponse> =apiService.getCharactersFromApi(name=name,offset =offset )
}
