package com.example.dictionarycompose.data.repository

import com.example.dictionarycompose.data.remote.DictionaryApiService
import com.example.dictionarycompose.data.remote.dto.WordInfoDTO
import com.example.dictionarycompose.domain.repository.WordInfoRepository
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApiService
) : WordInfoRepository {
    override suspend fun getWordInfo(word: String): List<WordInfoDTO> {
        return api.getWordInfo(word)
    }
}