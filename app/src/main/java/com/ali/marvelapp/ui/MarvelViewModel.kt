package com.ali.marvelapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.marvelapp.common.Resource
import com.ali.marvelapp.data.MarvelRepository
import com.ali.marvelapp.data.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarvelViewModel   @ViewModelInject constructor (private val marvelRepository: MarvelRepository) : ViewModel() {

     private var marvelsData = MutableLiveData<Resource<Data>>()

    //var error = MutableLiveData<Boolean>()



    init {
        getHomeData()

    }

    fun getHomeData() {
        marvelsData.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {

            val result = marvelRepository.getchars()
            marvelsData.postValue(Resource.Success(result))


        }


    }

    fun getData() : LiveData<Resource<Data>> = marvelsData

}
