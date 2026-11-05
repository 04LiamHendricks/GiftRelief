package com.giftrelief.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.giftrelief.app.data.local.SessionManager
import com.giftrelief.app.data.local.SettingsManager
import com.giftrelief.app.navigation.GiftReliefNavGraph
import com.giftrelief.app.ui.theme.GiftReliefTheme
import com.giftrelief.app.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(applicationContext)
        val settingsManager = SettingsManager(applicationContext)
        val factory = ViewModelFactory(applicationContext)

        setContent {
            val darkMode by settingsManager.darkModeFlow.collectAsState(initial = false)

            GiftReliefTheme(darkTheme = darkMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GiftReliefNavGraph(factory = factory, sessionManager = sessionManager)
                }
            }
        }
    }
}
