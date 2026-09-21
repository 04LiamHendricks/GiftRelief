package com.giftrelief.app.data.repository

import com.giftrelief.app.data.local.SessionManager
import com.giftrelief.app.data.model.AuthResponse
import com.giftrelief.app.data.model.LoginRequest
import com.giftrelief.app.data.model.RegisterRequest
import com.giftrelief.app.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}

/**
 * Contract consumed by the ViewModels. Extracted as an interface so tests
 * can swap in a fake implementation instead of mocking Retrofit/OkHttp.
 */
interface AuthRepositoryContract {
    suspend fun register(fullName: String, email: String, password: String): ApiResult<AuthResponse>
    suspend fun login(email: String, password: String): ApiResult<AuthResponse>
    fun logout()
    fun isLoggedIn(): Boolean
}

/**
 * Wraps every network call in a try/catch so that no IO, timeout or
 * server error can crash the UI - callers always get back an ApiResult.
 */
class AuthRepository(
    private val api: ApiService,
    private val sessionManager: SessionManager
) : AuthRepositoryContract {

    override suspend fun register(fullName: String, email: String, password: String): ApiResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.register(RegisterRequest(fullName.trim(), email.trim(), password))
                handleAuthResponse(response)
            } catch (e: IOException) {
                ApiResult.Error("Can't reach the server. Check your connection and try again.")
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Registration failed. Please try again.")
            }
        }

    override suspend fun login(email: String, password: String): ApiResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(LoginRequest(email.trim(), password))
                handleAuthResponse(response)
            } catch (e: IOException) {
                ApiResult.Error("Can't reach the server. Check your connection and try again.")
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Login failed. Please try again.")
            }
        }

    private fun handleAuthResponse(response: retrofit2.Response<AuthResponse>): ApiResult<AuthResponse> {
        return if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!
            sessionManager.saveToken(body.token)
            sessionManager.saveUserEmail(body.user.email)
            ApiResult.Success(body)
        } else {
            val message = when (response.code()) {
                401 -> "Incorrect email or password"
                409 -> "An account with this email already exists"
                in 500..599 -> "Server error. Please try again later"
                else -> "Something went wrong (code ${response.code()})"
            }
            ApiResult.Error(message)
        }
    }

    override fun logout() = sessionManager.clearSession()

    override fun isLoggedIn(): Boolean = sessionManager.isLoggedIn()
}
