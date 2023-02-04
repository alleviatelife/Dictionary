package com.example.dictionarycompose.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionarycompose.R
import com.example.dictionarycompose.domain.models.WordInfo
import com.example.dictionarycompose.presentation.theme.ui.DarkPink
import com.example.dictionarycompose.presentation.theme.ui.DarkerPink

@Composable
fun DictionaryItem(item: WordInfo, index: Int, onItemClick: (Int) -> Unit) {
    val borderColor = listOf(DarkPink, DarkerPink)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .clickable { onItemClick(index) }
            .border(width = 2.dp, Brush.horizontalGradient(borderColor), RoundedCornerShape(20))
            .padding(2.dp)
    ) {

        val superscript = SpanStyle(
            baselineShift = BaselineShift.Superscript,
            fontSize = 14.sp, // font size of superscript
            color = Color.Black // color
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)

        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        append(item.word)
                        withStyle(superscript) {
                            append("${index + 1}")
                        }
                    },
                    style = TextStyle(
                        color = Color.Black, fontSize = 16.sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.philosopher_bolditalic))
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.phonetic ?: "",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.philosopher_bolditalic))
                )
            }

            Text(
                text = item.meanings[0].definitions[0].definition,
                style = MaterialTheme.typography.body1,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(R.font.philosopher_bolditalic)),
                fontWeight = FontWeight.Light
            )


        }
    }
}