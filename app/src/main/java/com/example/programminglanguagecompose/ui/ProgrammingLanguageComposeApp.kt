package com.example.programminglanguagecompose.ui

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
import com.example.programminglanguagecompose.ui.navigation.keyId
import com.example.programminglanguagecompose.ui.screen.detail.DetailScreen
import com.example.programminglanguagecompose.ui.screen.favorite.FavoriteScreen
import com.example.programminglanguagecompose.ui.screen.home.HomeScreen
import com.example.programminglanguagecompose.ui.screen.profile.ProfileScreen

@Composable
fun ProgrammingLanguageComposeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailLanguage.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { keyId ->
                        navController.navigate(Screen.DetailLanguage.createRoute(keyId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { keyId ->
                        navController.navigate(Screen.DetailLanguage.createRoute(keyId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailLanguage.route,
                arguments = listOf(navArgument(keyId) { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt(keyId) ?: -1
                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
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