package com.ali.marvelapp.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali.marvelapp.common.COMICS
import com.ali.marvelapp.common.EVENTS
import com.ali.marvelapp.common.SERIES
import com.ali.marvelapp.common.STORIES
import com.ali.marvelapp.data.model.homeModel.Data
import com.ali.marvelapp.data.model.homeModel.MarvelResponse
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import com.ali.marvelapp.data.sources.remoteApi.ApiService
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val service: ApiService ) {

    lateinit var data: Response<MarvelResponse>


    private val comicsList = MutableLiveData<List<ResultsDetails>>()
    private val seriesList = MutableLiveData<List<ResultsDetails>>()
    private val storiesList = MutableLiveData<List<ResultsDetails>>()
    private val eventsList = MutableLiveData<List<ResultsDetails>>()


    suspend fun getHomeCharactersByName(named: String): Data =
        service.getCharactersFromApi(name = named).body()?.data!!


    suspend fun getHomeCharacters(offset: Int): Response<MarvelResponse> =
        service.getCharactersFromApi(offset = offset)


    suspend fun loadCharacterDetails(detailPath: String, characterId: Int) {
        try {

            coroutineScope {
                getListType(detailPath)?.postValue(
                    service.getCharacterDetails(detailPath, characterId).data?.results
                )
            }
        } catch (e: Exception) {
            Log.e("b", e.message.toString())
        }
    }


    private fun getListType(detailPath: String): MutableLiveData<List<ResultsDetails>>? {
        when (detailPath) {
            COMICS -> return comicsList
            SERIES -> return seriesList
            STORIES -> return storiesList
            EVENTS -> return eventsList
        }
        return null
    }


    fun getComicsList(): LiveData<List<ResultsDetails>> = comicsList

    fun getSeriesList(): LiveData<List<ResultsDetails>> = seriesList

    fun getStoriesList(): LiveData<List<ResultsDetails>> = storiesList

    fun getEventsList(): LiveData<List<ResultsDetails>> = eventsList

}