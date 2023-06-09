package com.example.programminglanguagecompose.ui.navigation

import com.example.programminglanguagecompose.utils.Const.navKeyId

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailLanguage : Screen("home/{$navKeyId}") {
        fun createRoute(navKeyId: Int) = "home/$navKeyId"
    }
}