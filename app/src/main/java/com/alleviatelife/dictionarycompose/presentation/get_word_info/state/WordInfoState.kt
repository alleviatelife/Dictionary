package com.alleviatelife.dictionarycompose.presentation.get_word_info.state

import com.alleviatelife.dictionarycompose.domain.models.WordInfo


data class WordInfoState(
    val isLoading: Boolean = false,
    val wordInfoList: List<WordInfo> = emptyList(),
    val error: String = "",
)
