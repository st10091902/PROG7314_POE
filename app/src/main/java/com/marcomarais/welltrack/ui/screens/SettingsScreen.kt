package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.marcomarais.welltrack.feature.settings.SettingsRepo
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val ctx = LocalContext.current
    val repo = remember { SettingsRepo(ctx) }
    val scope = rememberCoroutineScope()
    val settings by repo.flow.collectAsState(initial = null)

    Column(Modifier.padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (settings != null) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Dark mode")
                Switch(checked = settings!!.darkMode, onCheckedChange = {
                    scope.launch { repo.setDark(it) }
                })
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Daily reminders")
                Switch(checked = settings!!.dailyReminders, onCheckedChange = {
                    scope.launch { repo.setReminders(it) }
                })
            }
        } else {
            CircularProgressIndicator()
        }
    }
}
