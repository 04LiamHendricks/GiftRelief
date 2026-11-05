package com.giftrelief.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.giftrelief.app.data.local.SessionManager
import com.giftrelief.app.data.local.SettingsManager
import com.giftrelief.app.data.remote.RetrofitInstance
import com.giftrelief.app.data.repository.AuthRepository

/**
 * Simple manual DI factory (no Hilt/Koin dependency needed) that wires the
 * repository + local storage into each ViewModel.
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val sessionManager = SessionManager(context.applicationContext)
        val settingsManager = SettingsManager(context.applicationContext)
        val authRepository = AuthRepository(RetrofitInstance.api, sessionManager)

        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(settingsManager, authRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
