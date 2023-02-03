package com.example.dictionarycompose.domain.models

import com.example.dictionarycompose.data.remote.dto.Meaning
import com.example.dictionarycompose.data.remote.dto.Phonetic

data class WordInfo (
    val meanings: List<Meaning>,
    val phonetic: String?="",
    val phonetics: List<Phonetic>,
    val word: String
)