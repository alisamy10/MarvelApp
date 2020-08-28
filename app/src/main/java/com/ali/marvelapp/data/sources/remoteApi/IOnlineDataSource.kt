package com.ali.marvelapp.data.sources.remoteApi

import com.ali.marvelapp.data.model.Data


interface IOnlineDataSource {


   suspend fun getCharacters() : Data
}