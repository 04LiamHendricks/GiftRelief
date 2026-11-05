package com.giftrelief.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.giftrelief.app.ui.components.GiftTextField
import com.giftrelief.app.ui.components.PrimaryButton
import com.giftrelief.app.ui.theme.GiftGreenDark
import com.giftrelief.app.ui.theme.GiftTextSecondary
import com.giftrelief.app.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) onRegisterSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = GiftGreenDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Join GiftRelief and start making a difference",
            style = MaterialTheme.typography.bodyLarge,
            color = GiftTextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        GiftTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Full Name",
            placeholder = "Enter your full name",
            isError = uiState.fieldErrors.containsKey("fullName"),
            errorText = uiState.fieldErrors["fullName"]
        )

        Spacer(modifier = Modifier.height(18.dp))

        GiftTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            isError = uiState.fieldErrors.containsKey("email"),
            errorText = uiState.fieldErrors["email"]
        )

        Spacer(modifier = Modifier.height(18.dp))

        GiftTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "At least 8 characters",
            isPassword = true,
            isError = uiState.fieldErrors.containsKey("password"),
            errorText = uiState.fieldErrors["password"]
        )

        Spacer(modifier = Modifier.height(18.dp))

        GiftTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "Re-enter your password",
            isPassword = true,
            isError = uiState.fieldErrors.containsKey("confirmPassword"),
            errorText = uiState.fieldErrors["confirmPassword"]
        )

        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Sign Up",
            isLoading = uiState.isLoading,
            onClick = { viewModel.register(fullName, email, password, confirmPassword) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Already have an account? ", color = GiftTextSecondary)
            Text(
                text = "Log In",
                color = GiftGreenDark,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
    }
}
