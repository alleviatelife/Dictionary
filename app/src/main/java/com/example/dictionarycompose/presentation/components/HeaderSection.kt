package com.example.dictionarycompose.presentation.components

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import com.example.dictionarycompose.R
import com.example.dictionarycompose.presentation.get_word_info.state.WordInfoState
import com.example.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.example.dictionarycompose.presentation.theme.ui.DarkPink
import com.example.dictionarycompose.presentation.theme.ui.DarkerPink
import java.io.IOException

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HeaderSection(
    context: Context? = null,
    state: WordInfoState,
    viewModel: WordInfoViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }
    var visible by rememberSaveable {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp),
        backgroundColor = DarkPink
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                modifier = Modifier

                    .fillMaxWidth()
                    .wrapContentHeight(),
                value = searchText,
                textStyle = TextStyle(
                    fontSize = 16.sp,
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

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                if (state.wordInfoList.isNotEmpty())
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.wordInfoList[0].word,
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Font(R.font.philosopher_bolditalic))
                            )
                        )
                        if (!state.wordInfoList[0].phonetic.isNullOrEmpty())
                            Text(
                                text = "( ${state.wordInfoList[0].phonetic} )",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.philosopher_regular))
                                )
                            )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            if (!state.wordInfoList[0].phonetics.isNullOrEmpty() && !state.wordInfoList[0].phonetics[0].audio.isNullOrEmpty())
                                Button(modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkerPink),
                                    onClick = {
                                        val url = state.wordInfoList[0].phonetics[0].audio
                                        val mediaPlayer = MediaPlayer()
                                        mediaPlayer.setAudioAttributes(
                                            AudioAttributes.Builder()
                                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                                .build()
                                        )

                                        try {
                                            if (context != null) {
                                                mediaPlayer.setDataSource(context, Uri.parse(url))
                                                mediaPlayer.prepare()
                                                mediaPlayer.start()
                                            }

                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }

                                    }) {
                                    Column() {
                                        Icon(
                                            painter = painterResource(R.drawable.volume_up),
                                            contentDescription = "speak word button",
                                        )
                                    }


                                }
                        }
                    }

            }


        }

    }

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun HeaderSection2(context: Context, progress: Float) {
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.search_motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.volume_up),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = properties.value.color("background"),
                    shape = CircleShape
                )
                .layoutId("profile_pic")
        )
        Text(
            text = "Rishabh",
            fontSize = 24.sp,
            modifier = Modifier.layoutId("username"),
            color = properties.value.color("background")
        )
    }
}