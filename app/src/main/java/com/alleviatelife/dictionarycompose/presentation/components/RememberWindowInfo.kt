package com.alleviatelife.dictionarycompose.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.alleviatelife.dictionarycompose.common.WindowInfo

@Composable
fun RememberWindowInfo(): com.alleviatelife.dictionarycompose.common.WindowInfo {
    val configuration = LocalConfiguration.current
    return com.alleviatelife.dictionarycompose.common.WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Medium
            else -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Medium
            else -> com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}
