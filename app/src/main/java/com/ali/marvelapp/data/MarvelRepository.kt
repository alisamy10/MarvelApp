package com.ali.marvelapp.data


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ali.marvelapp.common.COMICS
import com.ali.marvelapp.common.EVENTS
import com.ali.marvelapp.common.SERIES
import com.ali.marvelapp.common.STORIES
import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.model.MarvelResponse
import com.ali.marvelapp.data.model.detailsModel.DetailsMarvelResponse
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import com.ali.marvelapp.data.sources.remoteApi.ApiService
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class MarvelRepository @Inject constructor(private val service: ApiService ) {



    lateinit var data: Response<MarvelResponse>


    private val comicsList= MutableLiveData<List<ResultsDetails>>()
    private val seriesList= MutableLiveData<List<ResultsDetails>>()
    private val storiesList= MutableLiveData<List<ResultsDetails>>()
    private val eventsList= MutableLiveData<List<ResultsDetails>>()



     suspend fun getCharactersByName(name: String): Data {
        return getData(name).body()?.data!!
    }


    suspend fun getHomeCharacters(offset: Int): Response<MarvelResponse> = service.getCharactersFromApi(offset = offset)


    private suspend fun getData(name: String?, offset: Int = 1): Response<MarvelResponse> {
        try {
            coroutineScope {
                data = service.getCharactersFromApi(name = name,offset =  offset)
            }
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
        }
        return data
    }


     suspend fun loadCharacterDetails(detailPath: String, characterId: Int) {
        try {

            coroutineScope {
                getListType(detailPath)?.postValue(
                    service.getCharacterDetails(detailPath,characterId).data?.results
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


    fun getComicsList(): MutableLiveData<List<ResultsDetails>> {
        return comicsList
    }

    fun getSeriesList(): MutableLiveData<List<ResultsDetails>> {
        return seriesList
    }

    fun getStoriesList(): MutableLiveData<List<ResultsDetails>> {
        return storiesList
    }

    fun getEventsList(): MutableLiveData<List<ResultsDetails>> {
        return eventsList
    }









}