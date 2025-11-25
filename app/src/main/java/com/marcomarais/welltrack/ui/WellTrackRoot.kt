package com.marcomarais.welltrack.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marcomarais.welltrack.feature.auth.AuthViewModel
import com.marcomarais.welltrack.ui.screens.BiometricScreen
import com.marcomarais.welltrack.ui.screens.FoodSearchScreen
import com.marcomarais.welltrack.ui.screens.HomeScreen
import com.marcomarais.welltrack.ui.screens.SettingsScreen
import com.marcomarais.welltrack.ui.screens.SignInScreen
import com.google.firebase.messaging.FirebaseMessaging


@Composable
fun WellTrackRoot(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute in setOf(
        Routes.HOME,
        Routes.FOOD_SEARCH,
        Routes.SETTINGS
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SIGN_IN,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.SIGN_IN) {
                SignInScreen(
                    onSignedIn = {
                        navController.navigate(Routes.BIOMETRIC) {
                            popUpTo(Routes.SIGN_IN) { inclusive = true }
                        }
                    },
                    vm = authViewModel
                )
            }

            composable(Routes.BIOMETRIC) {
                BiometricScreen(
                    onAuthSuccess = {
                        // Subscribe to notifications topic
                        FirebaseMessaging.getInstance()
                            .subscribeToTopic("welltrack-updates")
                            .addOnCompleteListener { task ->
                                android.util.Log.d(
                                    "WellTrackFCM",
                                    "Subscribed to welltrack-updates: ${task.isSuccessful}"
                                )
                            }

                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.BIOMETRIC) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.HOME) {
                HomeScreen(
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                    onOpenFood = { navController.navigate(Routes.FOOD_SEARCH) }
                )
            }

            composable(Routes.FOOD_SEARCH) {
                FoodSearchScreen()
            }

            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
