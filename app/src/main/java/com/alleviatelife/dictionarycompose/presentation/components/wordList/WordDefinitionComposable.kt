package com.alleviatelife.dictionarycompose.presentation.components.wordList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alleviatelife.dictionarycompose.domain.models.WordInfo

@Composable
fun WordDefinitionComposable(item: WordInfo) {
    Column() {
        Text(
            text = item.word,
            modifier = Modifier.padding(top = 15.dp),
            style = MaterialTheme.typography.h2
        )

        Text(
            text = item.phonetic ?: "",
            modifier = Modifier.padding(bottom = 6.dp),
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
    }
    Divider()

    item.meanings.forEach { meaning ->

        Text(text = meaning.partOfSpeech)


        meaning.definitions.forEachIndexed { i, definition ->
            Text(
                text = "${i + 1} ${definition.definition}",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}