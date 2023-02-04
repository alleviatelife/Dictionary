package com.example.dictionarycompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionarycompose.domain.models.WordInfo
import com.example.dictionarycompose.presentation.get_word_info.state.WordInfoState
import com.example.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel

@Composable
fun DictionaryDataSection(
    viewModel: WordInfoViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {

    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .layoutId("lazyColumn1")


    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),


            ) {
            itemsIndexed(state.wordInfoList) { index: Int, item: WordInfo ->
                Spacer(modifier = Modifier.height(8.dp))
                DictionaryItem(item, index){
                        onItemClick(it)
                }
            }
        }

    }

    if (state.error.isNotBlank()) {
        Text(
            text = state.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .layoutId("error_text")

        )
    }

    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.layoutId("loader"))
    }

}