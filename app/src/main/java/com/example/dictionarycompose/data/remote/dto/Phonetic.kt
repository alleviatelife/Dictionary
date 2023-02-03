package com.example.dictionarycompose.data.remote.dto

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)