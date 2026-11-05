package com.giftrelief.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giftrelief.app.data.local.SettingsManager
import com.giftrelief.app.data.repository.AuthRepositoryContract
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager,
    private val authRepository: AuthRepositoryContract
) : ViewModel() {

    val darkMode = settingsManager.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val notificationsEnabled = settingsManager.notificationsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val currency = settingsManager.currencyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "ZAR")

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch {
        settingsManager.setDarkMode(enabled)
    }

    fun setNotificationsEnabled(enabled: Boolean) = viewModelScope.launch {
        settingsManager.setNotificationsEnabled(enabled)
    }

    fun setCurrency(value: String) = viewModelScope.launch {
        settingsManager.setCurrency(value)
    }

    fun logout() = authRepository.logout()
}
