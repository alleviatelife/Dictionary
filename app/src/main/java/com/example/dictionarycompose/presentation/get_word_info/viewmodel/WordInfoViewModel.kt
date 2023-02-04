package com.example.dictionarycompose.presentation.get_word_info.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionarycompose.common.Resource
import com.example.dictionarycompose.domain.usecase.wordInfo.GetWordInfoUseCase
import com.example.dictionarycompose.presentation.get_word_info.state.WordInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase,
) : ViewModel() {

    private val _searchedQuery = mutableStateOf("")
    val searchedQuery: State<String> = _searchedQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _selectedWordIndex = mutableStateOf(-1)
    val selectedWordIndex: State<Int> = _selectedWordIndex


    fun onEvent(event: WordInfoEvent) {
        when (event) {
            is WordInfoEvent.WordChanged -> {
                _searchedQuery.value = event.word
                getWordInfo()
            }
            is WordInfoEvent.SelectedWord -> {
                _selectedWordIndex.value = event.index
            }
        }
    }

    private fun getWordInfo() {
        val word = _searchedQuery.value
        getWordInfoUseCase(word).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WordInfoState(wordInfoList = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value =
                        WordInfoState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = WordInfoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class WordInfoEvent() {
        data class WordChanged(val word: String) : WordInfoEvent()
        data class SelectedWord(val index: Int) : WordInfoEvent()
    }

}