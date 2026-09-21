package com.giftrelief.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giftrelief.app.data.local.SessionManager
import com.giftrelief.app.ui.screens.HomeScreen
import com.giftrelief.app.ui.screens.LoginScreen
import com.giftrelief.app.ui.screens.OnboardingScreen
import com.giftrelief.app.ui.screens.RegisterScreen
import com.giftrelief.app.ui.screens.SettingsScreen
import com.giftrelief.app.viewmodel.AuthViewModel
import com.giftrelief.app.viewmodel.SettingsViewModel
import com.giftrelief.app.viewmodel.ViewModelFactory

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val SETTINGS = "settings"
}

@Composable
fun GiftReliefNavGraph(factory: ViewModelFactory, sessionManager: SessionManager) {
    val navController: NavHostController = rememberNavController()
    val startDestination = if (sessionManager.isLoggedIn()) Routes.HOME else Routes.ONBOARDING

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onGetStarted = { navController.navigate(Routes.REGISTER) },
                onAlreadyHaveAccount = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.LOGIN) {
            val authViewModel: AuthViewModel = viewModel(factory = factory)
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0)
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onForgotPassword = { /* TODO: forgot-password flow */ }
            )
        }

        composable(Routes.REGISTER) {
            val authViewModel: AuthViewModel = viewModel(factory = factory)
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0)
                    }
                },
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(onOpenSettings = { navController.navigate(Routes.SETTINGS) })
        }

        composable(Routes.SETTINGS) {
            val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
            SettingsScreen(
                viewModel = settingsViewModel,
                onLoggedOut = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
