package com.alleviatelife.dictionarycompose.data.remote.dto

import androidx.annotation.Keep
import com.alleviatelife.dictionarycompose.domain.models.WordInfo

@Keep
data class WordInfoDTO(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)

fun WordInfoDTO.toWordInfo(): WordInfo {
    return WordInfo(
        meanings = meanings,
        phonetic=phonetic,
        phonetics=phonetics,
        word=word
    )
}

