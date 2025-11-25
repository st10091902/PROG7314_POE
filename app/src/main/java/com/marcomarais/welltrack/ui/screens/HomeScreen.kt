package com.marcomarais.welltrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marcomarais.welltrack.R

@Composable
fun HomeScreen(
    onOpenSettings: () -> Unit,
    onOpenFood: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onOpenFood,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.home_search_log_food))
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onOpenSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.settings_title))
        }
    }
}
