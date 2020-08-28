package com.ali.marvelapp.data.sources.remoteApi



import android.util.Log
import com.ali.marvelapp.data.model.Data
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class OnlineSourcesBasedRetroFit @Inject constructor (private val service: IApiHelper) : IOnlineDataSource {

    private var data = Data()

    override suspend fun getCharacters(): Data {
        try {

            // coroutineScope is needed, else in case of any network error, it will crash
            coroutineScope {
                data= service.getCharacters().data!!
            }
        } catch (e: Exception) {
           // errorMsg= e.message.toString()
            Log.e("a", e.message.toString())
        }
        return data
    }

}
