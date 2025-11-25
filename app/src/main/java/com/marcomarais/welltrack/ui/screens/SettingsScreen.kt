package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marcomarais.welltrack.feature.settings.SettingsRepo
import kotlinx.coroutines.launch
import com.marcomarais.welltrack.R

@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    val repo = remember { SettingsRepo(context) }
    val scope = rememberCoroutineScope()
    val settingsState by repo.flow.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Title
        Text(
            text = stringResource(id = R.string.settings_title),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (settingsState != null) {

            // DARK MODE TOGGLE
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.dark_mode))
                Switch(
                    checked = settingsState!!.darkMode,
                    onCheckedChange = { enabled ->
                        scope.launch { repo.setDark(enabled) }
                    }
                )
            }

            // DAILY REMINDERS TOGGLE
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(id = R.string.daily_reminders))
                Switch(
                    checked = settingsState!!.dailyReminders,
                    onCheckedChange = { enabled ->
                        scope.launch { repo.setReminders(enabled) }
                    }
                )
            }

        } else {
            // LOADING STATE
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
