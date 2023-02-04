@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.dictionarycompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.constraintlayout.compose.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dictionarycompose.presentation.components.wordDetail.WordDetailScreen
import com.example.dictionarycompose.presentation.components.wordList.SwipingStates
import com.example.dictionarycompose.presentation.components.wordList.WordListScreen
import com.example.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.example.dictionarycompose.presentation.theme.ui.DictionaryComposeTheme
import dagger.hilt.android.AndroidEntryPoint

var keyboardController: SoftwareKeyboardController? = null;

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterialApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryComposeTheme {



                val viewModel = viewModel<WordInfoViewModel>()
                val state = viewModel.state.value
                val context = LocalContext.current
                keyboardController = LocalSoftwareKeyboardController.current
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.WordListScreen.route
                ) {
                    composable(
                        route = Screen.WordListScreen.route
                    ) {

                        val swipingState =
                            rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
                        WordListScreen(
                            swipingState = swipingState,
                            context = context,
                            navController=navController
                        )
                    }

                    composable(
                        route = Screen.WordDetailScreen.route
                    ) {

                        val loginBackStackEntry = remember { navController.getBackStackEntry(route=Screen.WordListScreen.route) }
                        val viewModel: WordInfoViewModel = hiltViewModel(loginBackStackEntry)
                        WordDetailScreen(viewModel=viewModel)
                    }
                }


            }


        }
    }

}




