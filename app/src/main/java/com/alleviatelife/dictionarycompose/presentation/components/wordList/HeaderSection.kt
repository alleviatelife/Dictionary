package com.alleviatelife.dictionarycompose.presentation.components.wordList

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.alleviatelife.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.alleviatelife.dictionarycompose.presentation.theme.ui.DarkPink
import com.alleviatelife.dictionarycompose.presentation.theme.ui.DarkerPink
import com.alleviatelife.dictionarycompose.presentation.theme.ui.Pink
import com.alleviatelife.dictionarycompose.R
import java.io.IOException


@OptIn(ExperimentalMotionApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HeaderSectionPortrait(
    context: Context, progress: Float,
    viewModel: WordInfoViewModel = hiltViewModel(),
    keyboardController: SoftwareKeyboardController?,
    onItemClick: (Int) -> Unit
) {

    val state = viewModel.state.value

    var searchText by rememberSaveable {
        mutableStateOf("")
    }
    var visible by rememberSaveable {
        mutableStateOf(false)
    }

    MotionLayout(start = ConstraintSet {
        val card = createRefFor("header_card")
        val searchBar = createRefFor("search_bar")
        val searchedWord = createRefFor("word")
        val phonetic = createRefFor("phonetic")
        val speakButton = createRefFor("speak_button")
        val lazyColumn1 = createRefFor("lazyColumn1")
        val loader = createRefFor("loader")
        val errorText = createRefFor("error_text")


        val startGuideLineFraction = if (state.wordInfoList.isNotEmpty()) 0.28f else 0.12f

        val topGuideline = createGuidelineFromTop(startGuideLineFraction)

        constrain(card) {
            this.height = Dimension.fillToConstraints
            top.linkTo(parent.top, 0.dp)
            start.linkTo(parent.start, 0.dp)
            end.linkTo(parent.end, 0.dp)
            bottom.linkTo(topGuideline)
        }
        constrain(searchBar) {
            this.width = Dimension.matchParent
            this.top.linkTo(card.top, 16.dp)
            this.end.linkTo(card.end, 16.dp)
            this.start.linkTo(card.start, 16.dp)
        }
        constrain(searchedWord) {
            this.start.linkTo(searchBar.start)
            this.end.linkTo(searchBar.end)
            this.top.linkTo(searchBar.bottom, 8.dp)
        }
        constrain(phonetic) {
            this.start.linkTo(searchBar.start)
            this.end.linkTo(searchBar.end)
            this.top.linkTo(searchedWord.bottom, 8.dp)
        }
        constrain(speakButton) {
            this.start.linkTo(searchBar.start)
            this.end.linkTo(searchBar.end)
            this.top.linkTo(phonetic.bottom, 8.dp)
        }
        constrain(lazyColumn1) {
            this.start.linkTo(parent.start, 16.dp)
            this.end.linkTo(parent.end, 16.dp)
            this.top.linkTo(card.bottom)
            this.height = Dimension.fillToConstraints
            this.bottom.linkTo(parent.bottom)
        }
        constrain(loader) {
            this.start.linkTo(parent.start)
            this.end.linkTo(parent.end)
            this.top.linkTo(card.bottom)
            this.bottom.linkTo(parent.bottom)
        }
        constrain(errorText) {
            this.start.linkTo(parent.start)
            this.end.linkTo(parent.end)
            this.top.linkTo(card.bottom)
            this.bottom.linkTo(parent.bottom)
        }

    }, end = ConstraintSet {

        val card = createRefFor("header_card")
        val searchBar = createRefFor("search_bar")
        val searchedWord = createRefFor("word")
        val phonetic = createRefFor("phonetic")
        val speakButton = createRefFor("speak_button")

        val startGuideLineFraction = if (state.wordInfoList.isNotEmpty()) 0.18f else 0.12f
        val topGuideline = createGuidelineFromTop(startGuideLineFraction)
        val lazyColumn1 = createRefFor("lazyColumn1")
        val loader = createRefFor("loader")
        val errorText = createRefFor("error_text")

        constrain(card) {
            this.height = Dimension.fillToConstraints
            top.linkTo(parent.top, 0.dp)
            start.linkTo(parent.start, 0.dp)
            end.linkTo(parent.end, 0.dp)
            bottom.linkTo(topGuideline)
        }
        constrain(searchBar) {
            this.width = Dimension.matchParent
            this.top.linkTo(card.top, 16.dp)
            this.end.linkTo(card.end, 16.dp)
            this.start.linkTo(card.start, 16.dp)
        }
        constrain(searchedWord) {
            this.start.linkTo(searchBar.start, 8.dp)
            this.top.linkTo(searchBar.bottom, 12.dp)
        }
        constrain(phonetic) {
            this.start.linkTo(searchedWord.end, 8.dp)
            this.top.linkTo(searchedWord.top)
            this.bottom.linkTo(searchedWord.bottom)
        }
        constrain(speakButton) {
            this.end.linkTo(card.end, 16.dp)
            this.top.linkTo(phonetic.top)
            this.bottom.linkTo(phonetic.bottom)
        }
        constrain(lazyColumn1) {
            this.start.linkTo(parent.start, 16.dp)
            this.end.linkTo(parent.end, 16.dp)
            this.top.linkTo(card.bottom)
            this.height = Dimension.fillToConstraints
            this.bottom.linkTo(parent.bottom)
        }
        constrain(loader) {
            this.start.linkTo(parent.start)
            this.end.linkTo(parent.end)
            this.top.linkTo(card.bottom)
            this.bottom.linkTo(parent.bottom)
        }
        constrain(errorText) {
            this.start.linkTo(parent.start)
            this.end.linkTo(parent.end)
            this.top.linkTo(card.bottom)
            this.bottom.linkTo(parent.bottom)
        }
    },
        progress = progress,
        modifier = Modifier
            .fillMaxSize()

    ) {

        val colorList = arrayOf(
            0.3f to Pink,
            0.4f to DarkPink
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("header_card"),
            shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp),

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = colorList,
                            tileMode = TileMode.Mirror
                        )
                    ),
            )
        }

        TextField(
            modifier = Modifier
                .layoutId("search_bar"),
            value = searchText,
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                searchText = it
            },
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Clear Search bar",
                            tint = Color.LightGray
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (searchText.isNotEmpty()) {
                    viewModel.onEvent(WordInfoViewModel.WordInfoEvent.WordChanged(searchText))
                }
                keyboardController?.hide()
            }),
        )

        visible = state.wordInfoList.isNotEmpty()


        if (state.wordInfoList.isNotEmpty()) {
            val url =
                state.wordInfoList[0].phonetics.firstOrNull() { s -> !s.audio.isNullOrEmpty() }?.audio
            val word = state.wordInfoList.firstOrNull { s -> !s.word.isNullOrEmpty() }?.word
            Text(
                text = word ?: "",
                modifier = Modifier.layoutId("word"),
                color = Color.White,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.philosopher_bolditalic))
                )
            )
            val phonetic =
                state.wordInfoList.firstOrNull() { s -> !s.phonetic.isNullOrEmpty() }?.phonetic
            Text(
                text = phonetic ?: "",
                modifier = Modifier.layoutId("phonetic"),
                color = Color.White,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.philosopher_regular))
                )
            )



                Button(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .layoutId("speak_button"),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkerPink),
                    onClick = {

                        val mediaPlayer = MediaPlayer()
                        mediaPlayer.setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )

                        try {
                            if (context != null) {
                                mediaPlayer.setDataSource(
                                    context,
                                    Uri.parse(url)
                                )
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            }

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    },
                    enabled = (!url.isNullOrEmpty())
                ) {
                    Icon(
                        painter = painterResource(R.drawable.volume_up),
                        contentDescription = "speak word button",
                    )
                }
        }

        DictionaryDataSection() {
            onItemClick(it)
        }
    }
}