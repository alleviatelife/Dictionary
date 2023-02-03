package com.example.dictionarycompose.data.remote.dto

import com.example.dictionarycompose.domain.models.WordInfo

data class WordInfoDTO(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)

fun WordInfoDTO.toWordInfo():WordInfo{
    return WordInfo(
        meanings = meanings,
        phonetic=phonetic,
        phonetics=phonetics,
        word=word
    )
}

