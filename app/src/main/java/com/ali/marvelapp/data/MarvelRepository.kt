package com.ali.marvelapp.data


import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.sources.remoteApi.IOnlineDataSource
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val onlineDataSource: IOnlineDataSource ) {



    suspend fun getchars(): Data {


        var data= onlineDataSource.getCharacters()


        /* //sourcesList.postValue(Resource.Loading())
        return if (networkHandler.isOnline()) {
            offlineDataSource.deleteAllNews()
            val data = onlineDataSource.getArticles()
            //sourcesList.postValue(Resource.Success(data))
            offlineDataSource.cacheArticles(data)
            data
        } else {
            //sourcesList.postValue(Resource.Error(data = offlineDataSource.getArticles() , msg = OnlineSourcesBasedRetroFit.errorMsg))
            offlineDataSource.getArticles()
        }

        */

        return data
    }
}