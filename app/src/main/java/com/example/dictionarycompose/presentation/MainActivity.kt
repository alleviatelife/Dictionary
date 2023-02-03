@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.dictionarycompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionarycompose.presentation.components.HeaderSection2
import com.example.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.example.dictionarycompose.presentation.theme.ui.DictionaryComposeTheme
import dagger.hilt.android.AndroidEntryPoint

var keyboardController: SoftwareKeyboardController? = null;

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryComposeTheme {
                val viewModel = viewModel<WordInfoViewModel>()
                val state = viewModel.state.value
                val context = LocalContext.current
                keyboardController = LocalSoftwareKeyboardController.current
                val scrollState = rememberScrollState()
                Column(Modifier.verticalScroll(scrollState)) {

                    var animateToCollapsedState by remember { mutableStateOf(false) }
                    val progress by animateFloatAsState(
                        targetValue = if (animateToCollapsedState) 1f else 0f, // Based on boolean we change progress target
                        animationSpec = tween(1000) // specifying animation type - Inbetweening animation with 1000ms duration
                    )
                    Spacer(modifier = Modifier.height(200.dp))
                    HeaderSection2(context = context, progress = progress)



                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {


}