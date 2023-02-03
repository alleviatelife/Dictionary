package com.example.dictionarycompose.data.remote

import com.example.dictionarycompose.data.remote.dto.WordInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {

    @GET("api/v2/entries/en/{word}")
    suspend fun getWordInfo(@Path("word") word:String): List<WordInfoDTO>

}