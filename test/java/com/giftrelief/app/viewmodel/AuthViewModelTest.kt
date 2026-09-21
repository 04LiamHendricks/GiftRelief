package com.giftrelief.app.viewmodel

import com.giftrelief.app.data.model.AuthResponse
import com.giftrelief.app.data.model.User
import com.giftrelief.app.data.repository.ApiResult
import com.giftrelief.app.data.repository.AuthRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * A fake repository so these tests run fully offline and never touch
 * Retrofit/OkHttp - only AuthViewModel's own logic is under test.
 */
private class FakeAuthRepository(
    private val loginResult: ApiResult<AuthResponse>? = null,
    private val registerResult: ApiResult<AuthResponse>? = null
) : AuthRepositoryContract {

    var loginCalled = false
    var registerCalled = false

    override suspend fun register(fullName: String, email: String, password: String): ApiResult<AuthResponse> {
        registerCalled = true
        return registerResult ?: ApiResult.Error("not configured")
    }

    override suspend fun login(email: String, password: String): ApiResult<AuthResponse> {
        loginCalled = true
        return loginResult ?: ApiResult.Error("not configured")
    }

    override fun logout() {}
    override fun isLoggedIn(): Boolean = false
}

private val sampleAuthResponse = AuthResponse(
    token = "fake-token",
    user = User(id = "1", fullName = "Test User", email = "test@example.com")
)

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with invalid email does not call repository`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.login("not-an-email", "somepassword")

        assertFalse(repo.loginCalled)
        assertTrue(viewModel.uiState.value.fieldErrors.containsKey("email"))
    }

    @Test
    fun `login with blank password shows field error`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.login("user@example.com", "")

        assertFalse(repo.loginCalled)
        assertTrue(viewModel.uiState.value.fieldErrors.containsKey("password"))
    }

    @Test
    fun `successful login updates state to success`() = runTest {
        val repo = FakeAuthRepository(loginResult = ApiResult.Success(sampleAuthResponse))
        val viewModel = AuthViewModel(repo)

        viewModel.login("user@example.com", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(repo.loginCalled)
        assertTrue(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun `failed login surfaces server error message`() = runTest {
        val repo = FakeAuthRepository(loginResult = ApiResult.Error("Incorrect email or password"))
        val viewModel = AuthViewModel(repo)

        viewModel.login("user@example.com", "wrongpassword")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Incorrect email or password", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun `register with mismatched passwords does not call repository`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.register("Jane Doe", "jane@example.com", "password123", "password124")

        assertFalse(repo.registerCalled)
        assertTrue(viewModel.uiState.value.fieldErrors.containsKey("confirmPassword"))
    }

    @Test
    fun `register with weak password does not call repository`() = runTest {
        val repo = FakeAuthRepository()
        val viewModel = AuthViewModel(repo)

        viewModel.register("Jane Doe", "jane@example.com", "abc", "abc")

        assertFalse(repo.registerCalled)
        assertTrue(viewModel.uiState.value.fieldErrors.containsKey("password"))
    }

    @Test
    fun `successful register updates state to success`() = runTest {
        val repo = FakeAuthRepository(registerResult = ApiResult.Success(sampleAuthResponse))
        val viewModel = AuthViewModel(repo)

        viewModel.register("Jane Doe", "jane@example.com", "password123", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(repo.registerCalled)
        assertTrue(viewModel.uiState.value.isSuccess)
    }
}
