package com.ali.marvelapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.marvelapp.common.*
import com.ali.marvelapp.data.MarvelRepository
import com.ali.marvelapp.data.model.homeModel.Data
import com.ali.marvelapp.data.model.homeModel.MarvelResponse
import com.ali.marvelapp.data.model.detailsModel.ResultsDetails
import com.ali.marvelapp.data.sources.remoteApi.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class MarvelViewModel   @ViewModelInject constructor (private val marvelRepository: MarvelRepository , private val service: ApiService) : ViewModel() {

     private var marvelsData = MutableLiveData<Resource<MarvelResponse>>()




    private var searchData = MutableLiveData<Resource<Data>>()

    private var marvelResponse: MarvelResponse? = null
    var homePage= 0



    init {
        getHomeData()
    }



     fun getHomeData() {
        marvelsData.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.IO) {
                val result = marvelRepository.getHomeCharacters(homePage)
                marvelsData.postValue(handleMarvelResponse(result))

            }
    }

    fun getData() : LiveData<Resource<MarvelResponse>> = marvelsData



    private fun handleMarvelResponse(response: Response<MarvelResponse>) : Resource<MarvelResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                homePage++
                if(marvelResponse == null) {
                    marvelResponse = resultResponse
                } else {

                    var oldData = marvelResponse?.data?.results
                    var newAData = resultResponse.data?.results

                    newAData?.let { oldData?.addAll(it) }
                }
                return Resource.Success(marvelResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



     fun loadDetails(id:Int) {
         viewModelScope.launch(Dispatchers.IO) {

             marvelRepository.loadCharacterDetails(COMICS, id)
             marvelRepository.loadCharacterDetails(SERIES, id)
             marvelRepository.loadCharacterDetails(STORIES, id)
             marvelRepository.loadCharacterDetails(EVENTS, id)
         }

    }


    fun getPagerList(listType: String?): LiveData<List<ResultsDetails>>? {
        when (listType) {
                   COMICS -> return getComicsList()
                   SERIES -> return getSeriesList()
                   STORIES -> return getStoriesList()
                   EVENTS -> return getEventsList()
        }
        return null
    }




    fun getComicsList(): LiveData<List<ResultsDetails>> {
        return marvelRepository.getComicsList()
    }

    fun getSeriesList(): LiveData<List<ResultsDetails>> {
        return marvelRepository.getSeriesList()
    }

    fun getStoriesList(): LiveData<List<ResultsDetails>> {
        return marvelRepository.getStoriesList()
    }

    fun getEventsList(): LiveData<List<ResultsDetails>> {
        return marvelRepository.getEventsList()
    }


    fun findCharactersByName(name: String) {
        searchData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO){

            val result =marvelRepository.getHomeCharactersByName(named = name)
            searchData.postValue(Resource.Success(result))
        }
    }
    fun getSearchList(): MutableLiveData<Resource<Data>> = searchData



}
