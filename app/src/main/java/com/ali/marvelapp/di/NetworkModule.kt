package com.ali.marvelapp.di

import com.ali.marvelapp.BuildConfig
import com.ali.marvelapp.data.sources.remoteApi.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor()=   Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .url(
                chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apikey", BuildConfig.apikey)
                    .addQueryParameter("hash",BuildConfig.hash)


                    .build()
            )
            .build()
        return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor)=   OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()




    @Provides
    @Singleton
    fun provideGsonConverterFactory()=  GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideCoroutineCallAdapterFactory()= CoroutineCallAdapterFactory()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gsonConverterFactory: GsonConverterFactory, coroutineCallAdapterFactory: CoroutineCallAdapterFactory
                               , okHttpClient: OkHttpClient
    ) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) =retrofit.create(ApiService::class.java)


}

