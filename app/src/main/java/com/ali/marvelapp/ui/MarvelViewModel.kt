package com.ali.marvelapp.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.marvelapp.common.Resource
import com.ali.marvelapp.data.MarvelRepository
import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.model.MarvelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class MarvelViewModel   @ViewModelInject constructor (private val marvelRepository: MarvelRepository) : ViewModel() {

     private var marvelsData = MutableLiveData<Resource<MarvelResponse>>()

    private var searchData = MutableLiveData<Resource<Data>>()

    var marvelResponse: MarvelResponse? = null
    var homePage=0



    init {
        getHomeData()
    }

     fun getHomeData() {
        marvelsData.postValue(Resource.Loading())

            viewModelScope.launch(Dispatchers.IO) {
                val result = marvelRepository.getChars(homePage)
                marvelsData.postValue(handleBreakingNewsResponse(result))

            }
    }


    fun getData() : LiveData<Resource<MarvelResponse>> = marvelsData


    fun getSearchList(): LiveData<Resource<Data>> = searchData

    fun findCharactersByName(name: String) {
        viewModelScope.launch(Dispatchers.IO){
            val result =marvelRepository.searchByName(name)
            searchData.postValue(Resource.Success(result))
        }
    }


    private fun handleBreakingNewsResponse(response: Response<MarvelResponse>) : Resource<MarvelResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                homePage++
                if(marvelResponse == null) {
                    marvelResponse = resultResponse
                } else {

                    var oldArticles = marvelResponse?.data
                    var newArticles = resultResponse.data
                    Log.e("a",oldArticles.toString())
                    Log.e("a",newArticles.toString())
                }
                return Resource.Success(marvelResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}
