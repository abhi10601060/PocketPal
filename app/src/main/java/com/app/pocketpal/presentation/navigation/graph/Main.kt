package com.app.pocketpal.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.app.pocketpal.presentation.screens.main_screen.MainScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.mainNavigation(navController: NavController){
    navigation<MainNavRoute>( startDestination = MainScreenRoute) {
        composable<MainScreenRoute> {
            MainScreen(isDarkTheme = false)
        }
    }
}

@Serializable
object MainNavRoute{}

@Serializable
object MainScreenRoute{}

@Serializable
object InputScreenRoute{}