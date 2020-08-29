package  com.ali.marvelapp.di


import com.ali.marvelapp.data.MarvelRepository
import com.ali.marvelapp.data.sources.remoteApi.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideMarvelRepository(iOnlineDataSource: IOnlineDataSource)
            = MarvelRepository(iOnlineDataSource)


    @Provides
    @Singleton
    fun provideIOnlineDataSource( service: IApiHelper):IOnlineDataSource
            = OnlineSourcesBasedRetroFit(service)




    @Provides
    @Singleton
    fun provideIApiHelper( apiService: ApiService):IApiHelper
            = ApiHelperImpl(apiService)







}



