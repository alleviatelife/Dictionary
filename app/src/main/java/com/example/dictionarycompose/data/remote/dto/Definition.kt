package com.example.dictionarycompose.data.remote.dto

data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
)