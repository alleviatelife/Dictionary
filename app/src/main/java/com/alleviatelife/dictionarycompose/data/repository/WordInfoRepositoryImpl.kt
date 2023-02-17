package com.alleviatelife.dictionarycompose.data.repository


import com.alleviatelife.dictionarycompose.data.remote.DictionaryApiService
import com.alleviatelife.dictionarycompose.data.remote.dto.WordInfoDTO
import com.alleviatelife.dictionarycompose.domain.repository.WordInfoRepository
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApiService
) : WordInfoRepository {
    override suspend fun getWordInfo(word: String): List<WordInfoDTO> {
        return api.getWordInfo(word)
    }
}