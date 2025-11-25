package com.marcomarais.welltrack.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marcomarais.welltrack.ui.screens.HomeScreen
import com.marcomarais.welltrack.ui.screens.FoodSearchScreen
import com.marcomarais.welltrack.ui.screens.SettingsScreen
import com.marcomarais.welltrack.ui.screens.SignInScreen

@Composable
fun AppNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("signin") {
            SignInScreen(onSignedIn = {
                navController.navigate("home") {
                    popUpTo("signin") { inclusive = true }
                }
            })
        }

        composable("home") {
            HomeScreen(
                onOpenSettings = { navController.navigate("settings") },
                onOpenFood = { navController.navigate("foodSearch") }
            )
        }

        composable("foodSearch") { FoodSearchScreen() }

        composable("settings") { SettingsScreen() }
    }
}
