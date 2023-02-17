package com.alleviatelife.dictionarycompose.presentation

sealed class Screen(val route: String) {
    object WordListScreen : Screen("word_list_screen")
    object WordDetailScreen : Screen("word_detail_screen")
}
