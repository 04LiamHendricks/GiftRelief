package com.giftrelief.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giftrelief.app.data.repository.ApiResult
import com.giftrelief.app.data.repository.AuthRepositoryContract
import com.giftrelief.app.util.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val fieldErrors: Map<String, String> = emptyMap(),
    val isSuccess: Boolean = false
)

/**
 * Shared by both the Login and Register screens. Validates input locally
 * before ever hitting the network, and never lets an exception escape -
 * every failure path lands in AuthUiState.errorMessage instead.
 */
class AuthViewModel(private val repository: AuthRepositoryContract) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        val errors = mutableMapOf<String, String>()
        ValidationUtils.validateEmail(email)?.let { errors["email"] = it }
        if (password.isBlank()) errors["password"] = "Please enter your password"

        if (errors.isNotEmpty()) {
            _uiState.value = AuthUiState(fieldErrors = errors)
            return
        }

        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.login(email, password)) {
                is ApiResult.Success -> _uiState.value = AuthUiState(isSuccess = true)
                is ApiResult.Error -> _uiState.value = AuthUiState(errorMessage = result.message)
            }
        }
    }

    fun register(fullName: String, email: String, password: String, confirmPassword: String) {
        val errors = mutableMapOf<String, String>()
        ValidationUtils.validateFullName(fullName)?.let { errors["fullName"] = it }
        ValidationUtils.validateEmail(email)?.let { errors["email"] = it }
        ValidationUtils.validatePassword(password)?.let { errors["password"] = it }
        ValidationUtils.validateConfirmPassword(password, confirmPassword)?.let { errors["confirmPassword"] = it }

        if (errors.isNotEmpty()) {
            _uiState.value = AuthUiState(fieldErrors = errors)
            return
        }

        _uiState.value = AuthUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.register(fullName, email, password)) {
                is ApiResult.Success -> _uiState.value = AuthUiState(isSuccess = true)
                is ApiResult.Error -> _uiState.value = AuthUiState(errorMessage = result.message)
            }
        }
    }

    fun consumeError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
