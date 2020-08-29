package com.ali.marvelapp.data.sources.remoteApi



import android.util.Log
import com.ali.marvelapp.data.model.Data
import com.ali.marvelapp.data.model.MarvelResponse
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class OnlineSourcesBasedRetroFit @Inject constructor (private val service: IApiHelper) : IOnlineDataSource  {

    lateinit var data :Response<MarvelResponse>



    override suspend fun getCharactersByName(name: String): Data {
        return getData(name).body()?.data!!
    }

     override suspend fun getCharacters(offset:Int): Response<MarvelResponse> {
         return getData(null,offset)
    }

    private suspend fun  getData (name :String ? , offset:Int=1):Response<MarvelResponse>{
        try {
            coroutineScope {
                data= service.getCharacters(name,offset)
            }
        } catch (e: Exception) {
            Log.e("a", e.message.toString())
        }
        return data
    }

}
