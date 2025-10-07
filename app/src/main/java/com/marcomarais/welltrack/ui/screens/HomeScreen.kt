package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenSettings: () -> Unit,
    onOpenFood: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("WellTrack – Home", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onOpenFood, modifier = Modifier.fillMaxWidth()) { Text("Search/Log Food") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenSettings, modifier = Modifier.fillMaxWidth()) { Text("Settings") }
    }
}
