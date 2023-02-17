package com.alleviatelife.dictionarycompose.presentation.components.wordList

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alleviatelife.dictionarycompose.presentation.Screen
import com.alleviatelife.dictionarycompose.presentation.get_word_info.viewmodel.WordInfoViewModel
import com.alleviatelife.dictionarycompose.presentation.keyboardController
import com.alleviatelife.dictionarycompose.presentation.theme.ui.BabyPink


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun WordListScreen(
    swipingState: SwipeableState<SwipingStates>,
    context: Context,
    navController: NavController,
    viewModel: WordInfoViewModel = hiltViewModel()

) {
    val colorList = arrayOf(
        0.2f to Color.White,
        0.85f to BabyPink
    )

    BoxWithConstraints(//to get the max height
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colorStops = colorList))
    ) {
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    return swipingState.performDrag(delta).toOffset()

                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y
                    return swipingState.performDrag(delta).toOffset()
                }

                override suspend fun onPreFling(available: Velocity): Velocity {
                    swipingState.performFling(velocity = available.y)
                    return super.onPreFling(available)

                }

//                override suspend fun onPostFling(
//                    consumed: Velocity,
//                    available: Velocity
//                ): Velocity {
//                    swipingState.performFling(velocity = consumed.y)
//                    return super.onPostFling(consumed, available)
//                }

                private fun Float.toOffset() = Offset(0f, this)
            }
        }

        Box(//root container
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.0f)//it can be 0.5 in general
                    },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,//min height is collapsed
                        heightInPx to SwipingStates.EXPANDED,//max height is expanded
                    )
                )
                .nestedScroll(nestedScrollConnection)
        ) {

            val computedProgress by remember {//progress value will be decided as par state
                derivedStateOf {
                    if (swipingState.progress.to == SwipingStates.COLLAPSED)
                        swipingState.progress.fraction
                    else
                        1f - swipingState.progress.fraction
                }
            }
            HeaderSectionPortrait(
                context = context,
                progress = computedProgress,
                keyboardController = keyboardController,
            ) {
                viewModel.onEvent(WordInfoViewModel.WordInfoEvent.SelectedWord(it))
                navController.navigate(route = Screen.WordDetailScreen.route)
            }


        }
    }

}

enum class SwipingStates {
    //our own enum class for stoppages e.g. expanded and collapsed
    EXPANDED,
    COLLAPSED
}


