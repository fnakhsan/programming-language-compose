package com.example.programminglanguagecompose.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.programminglanguagecompose.R.string
import com.example.programminglanguagecompose.ui.navigation.NavigationItem
import com.example.programminglanguagecompose.ui.navigation.Screen
import com.example.programminglanguagecompose.ui.navigation.keyName
import com.example.programminglanguagecompose.ui.screen.detail.DetailScreen
import com.example.programminglanguagecompose.ui.screen.favorite.FavoriteScreen
import com.example.programminglanguagecompose.ui.screen.home.HomeScreen
import com.example.programminglanguagecompose.ui.screen.profile.ProfileScreen
import com.example.programminglanguagecompose.utils.Tag

@Composable
fun ProgrammingLanguageComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = Modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { languageName ->
                        navController.navigate(Screen.DetailLanguage.createRoute(languageName))
                        Log.d(Tag.repository, "This is the value that i pass to the detail: $languageName")
                    }
                )
            }
            composable(
                route = Screen.DetailLanguage.route,
                arguments = listOf(navArgument("languageName") { type = NavType.StringType }),
            ) {
                val name = it.arguments?.getString("languageName") ?: ""
                Log.d(Tag.repository, "And this is what i got: $name")
                DetailScreen(
                    name = name,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(string.favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(string.profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}