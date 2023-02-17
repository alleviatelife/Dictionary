package com.alleviatelife.dictionarycompose.domain.repository

import com.alleviatelife.dictionarycompose.data.remote.dto.WordInfoDTO


interface WordInfoRepository {

    suspend fun getWordInfo(word:String):List<WordInfoDTO>
}