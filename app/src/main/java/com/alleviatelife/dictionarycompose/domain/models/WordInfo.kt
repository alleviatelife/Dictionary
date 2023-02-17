package com.alleviatelife.dictionarycompose.domain.models

import com.alleviatelife.dictionarycompose.data.remote.dto.Meaning
import com.alleviatelife.dictionarycompose.data.remote.dto.Phonetic


data class WordInfo (
    val meanings: List<Meaning>,
    val phonetic: String?="",
    val phonetics: List<Phonetic>,
    val word: String
)