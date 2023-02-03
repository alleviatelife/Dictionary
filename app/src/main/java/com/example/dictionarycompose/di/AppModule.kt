package com.example.dictionarycompose.di

import com.example.dictionarycompose.common.Constants
import com.example.dictionarycompose.data.remote.DictionaryApiService
import com.example.dictionarycompose.data.repository.WordInfoRepositoryImpl
import com.example.dictionarycompose.domain.repository.WordInfoRepository
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
    fun provideDictionaryApiService():DictionaryApiService{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(api:DictionaryApiService):WordInfoRepository{
        return WordInfoRepositoryImpl(api)
    }
}