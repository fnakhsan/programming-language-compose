package com.example.programminglanguagecompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailLanguage : Screen("home/{name}") {
        fun createRoute(name: String) = "home/$name"
    }
}