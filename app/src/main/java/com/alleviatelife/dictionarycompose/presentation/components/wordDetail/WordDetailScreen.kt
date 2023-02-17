package com.alleviatelife.dictionarycompose.presentation.components.wordDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alleviatelife.dictionarycompose.data.remote.dto.Meaning
import com.alleviatelife.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.alleviatelife.dictionarycompose.presentation.theme.ui.*
import com.alleviatelife.dictionarycompose.R

@Composable
fun WordDetailScreen(viewModel: WordInfoViewModel = hiltViewModel()) {
    val selectedWordIndex = viewModel.selectedWordIndex.value
    val state = viewModel.state.value
    val colorList = arrayOf(
        0.3f to Pink, 0.4f to DarkPink
    )
    val colorListBackground = arrayOf(
        0.2f to Color.White,
        0.85f to BabyPink
    )

    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript, fontSize = 14.sp, color = Color.White
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colorStops = colorListBackground))
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = colorList, tileMode = TileMode.Mirror
                        )
                    ),
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(state.wordInfoList[selectedWordIndex].word)
                        withStyle(superscript) {
                            append("${selectedWordIndex + 1}")
                        }
                    }, color = Color.White, style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.philosopher_bolditalic))
                    ), modifier = Modifier.align(Alignment.Center)

                )
            }
        }



        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
        ) {
            items(state.wordInfoList[selectedWordIndex].meanings) { meaning: Meaning ->
                WordMeaningItemComposable(meaning = meaning)
                MeaningItem(meaning = meaning)
            }

        }
    }
}


@Composable
fun WordMeaningItemComposable(meaning: Meaning) {

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "pointer",
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = meaning.partOfSpeech,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            style = TextStyle(
                color = RoyalBlue, fontSize = 18.sp, fontWeight = FontWeight.SemiBold
            ),
        )
    }
    Spacer(modifier = Modifier.height(4.dp))

}

@Composable
fun MeaningItem(meaning: Meaning) {
    meaning.definitions.forEachIndexed { index, definition ->

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column() {
                Text(
                    text = "${index + 1}. ${definition.definition}",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    style = TextStyle(
                        color = Color.DarkGray, fontSize = 18.sp, fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.courgette_regular, FontWeight.Normal))
                    ),


                    )
                Spacer(modifier = Modifier.height(8.dp))
                if (!definition.example.isNullOrEmpty()) {
                    Row() {
                        Text(
                            text = "Example: ",
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                            style = TextStyle(
                                color = DarkerPink,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            fontFamily = FontFamily.Cursive
                        )
                        Text(
                            text = definition.definition,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            style = TextStyle(
                                color = DarkerPink,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            fontFamily = FontFamily.Cursive
                        )
                    }
                }
            }
        }
    }
}






