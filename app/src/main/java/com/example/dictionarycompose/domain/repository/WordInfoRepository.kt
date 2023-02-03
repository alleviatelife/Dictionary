package com.example.dictionarycompose.domain.repository

import com.example.dictionarycompose.data.remote.dto.WordInfoDTO

interface WordInfoRepository {

    suspend fun getWordInfo(word:String):List<WordInfoDTO>
}