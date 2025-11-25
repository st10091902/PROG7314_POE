package com.marcomarais.welltrack.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Routes used in Navigation
object Routes {
    const val SIGN_IN = "sign_in"
    const val HOME = "home"
    const val FOOD_SEARCH = "food_search"
    const val SETTINGS = "settings"
    const val BIOMETRIC = "biometric"
}

// One tab in the bottom navigation bar
data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

// List of tabs for the bottom bar
val bottomNavItems = listOf(
    NavItem(
        route = Routes.HOME,
        icon = Icons.Default.Home,
        label = "Home"
    ),
    NavItem(
        route = Routes.FOOD_SEARCH,
        icon = Icons.Default.Search,
        label = "Search"
    ),
    NavItem(
        route = Routes.SETTINGS,
        icon = Icons.Default.Settings,
        label = "Settings"
    )
)
