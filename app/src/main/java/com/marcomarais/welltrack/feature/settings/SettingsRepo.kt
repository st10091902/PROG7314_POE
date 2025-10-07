package com.marcomarais.welltrack.feature.settings

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

object Keys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val DAILY_REMINDERS = booleanPreferencesKey("daily_reminders")
}

data class Settings(val darkMode: Boolean, val dailyReminders: Boolean)

class SettingsRepo(private val context: Context) {
    val flow = context.dataStore.data.map { p: Preferences ->
        Settings(
            darkMode = p[Keys.DARK_MODE] ?: false,
            dailyReminders = p[Keys.DAILY_REMINDERS] ?: true
        )
    }
    suspend fun setDark(enabled: Boolean) = context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    suspend fun setReminders(enabled: Boolean) = context.dataStore.edit { it[Keys.DAILY_REMINDERS] = enabled }
}
