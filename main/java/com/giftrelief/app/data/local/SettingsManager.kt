package com.giftrelief.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "giftrelief_settings")

/**
 * Non-sensitive app preferences (theme, currency, notifications).
 * Kept separate from SessionManager, which only ever holds the auth token.
 */
class SettingsManager(private val context: Context) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        private val CURRENCY = stringPreferencesKey("currency")
    }

    val darkModeFlow: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_MODE] ?: false }

    val notificationsFlow: Flow<Boolean> =
        context.dataStore.data.map { it[NOTIFICATIONS_ENABLED] ?: true }

    val currencyFlow: Flow<String> =
        context.dataStore.data.map { it[CURRENCY] ?: "ZAR" }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setCurrency(currency: String) {
        context.dataStore.edit { it[CURRENCY] = currency }
    }
}
