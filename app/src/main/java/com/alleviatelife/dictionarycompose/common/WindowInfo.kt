package com.alleviatelife.dictionarycompose.common

import androidx.compose.ui.unit.Dp

data class WindowInfo(
    val screenWidthInfo: com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType,
    val screenHeightInfo: com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        object Compact: com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType()
        object Medium: com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType()
        object Expanded: com.alleviatelife.dictionarycompose.common.WindowInfo.WindowType()
    }
}

