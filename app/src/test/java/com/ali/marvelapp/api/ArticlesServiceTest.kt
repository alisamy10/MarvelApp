package com.ali.marvelapp.api


import com.ali.marvelapp.data.model.homeModel.Results
import com.ali.marvelapp.data.sources.remoteApi.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class ArticlesServiceTest : BaseServiceTest<ApiService>() {

    private lateinit var service: ApiService
    private lateinit var results: Results


    @Before
    fun initService() {
        service = createService(ApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchArticlesListTest()   {
        enqueueResponse("/MarvelsResponse.json")
        runBlocking {
            results = requireNotNull(service.getCharactersFromApi().body()?.data?.results?.get(0))
        }
        mockWebServer.takeRequest()

        assertThat(results.name,`is`("3-D Man"))


    }


}
