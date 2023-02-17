package com.alleviatelife.dictionarycompose.presentation.components.wordList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.alleviatelife.dictionarycompose.domain.models.WordInfo
import com.alleviatelife.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel

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