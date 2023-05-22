package com.example.programminglanguagecompose.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.programminglanguagecompose.assertCurrentRouteName
import com.example.programminglanguagecompose.data.model.LanguagesData
import com.example.programminglanguagecompose.ui.navigation.Screen
import com.example.programminglanguagecompose.ui.theme.ProgrammingLanguageComposeTheme
import com.example.programminglanguagecompose.utils.Const.tagTestList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProgrammingLanguageComposeAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private val dummyLanguage = LanguagesData.listData[9]

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ProgrammingLanguageComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                ProgrammingLanguageComposeApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, currentRoute)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag(tagTestList).performScrollToIndex(9)
        composeTestRule.onNodeWithText(dummyLanguage.name).performClick()
        navController.assertCurrentRouteName(Screen.DetailLanguage.route)
        composeTestRule.onNodeWithText(dummyLanguage.name).assertIsDisplayed()
    }
}