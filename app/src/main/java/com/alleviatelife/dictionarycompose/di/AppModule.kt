package com.alleviatelife.dictionarycompose.di

import com.alleviatelife.dictionarycompose.data.remote.DictionaryApiService
import com.alleviatelife.dictionarycompose.data.repository.WordInfoRepositoryImpl
import com.alleviatelife.dictionarycompose.domain.repository.WordInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDictionaryApiService(): DictionaryApiService {
        return Retrofit
            .Builder()
            .baseUrl(com.alleviatelife.dictionarycompose.common.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(api:DictionaryApiService): WordInfoRepository {
        return WordInfoRepositoryImpl(api)
    }
}