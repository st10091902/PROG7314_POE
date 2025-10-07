package com.marcomarais.welltrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcomarais.welltrack.ui.screens.*
import com.marcomarais.welltrack.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = Routes.SignIn) {
                        composable(Routes.SignIn) {
                            SignInScreen(onSignedIn = { nav.navigate(Routes.Home) })
                        }
                        composable(Routes.Home) {
                            HomeScreen(
                                onOpenSettings = { nav.navigate(Routes.Settings) },
                                onOpenFood = { nav.navigate(Routes.FoodSearch) }
                            )
                        }
                        composable(Routes.Settings) { SettingsScreen() }
                        composable(Routes.FoodSearch) { FoodSearchScreen() }
                    }
                }
            }
        }
    }
}

object Routes {
    const val SignIn = "sign_in"
    const val Home = "home"
    const val Settings = "settings"
    const val FoodSearch = "food_search"
}
