package com.giftrelief.app.util

/**
 * Centralised input validation so the UI never crashes on, or silently
 * accepts, malformed input. Each function returns a human-readable error
 * message, or null if the input is valid.
 */
object ValidationUtils {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validateFullName(name: String): String? {
        return when {
            name.isBlank() -> "Please enter your full name"
            name.trim().length < 2 -> "Name is too short"
            else -> null
        }
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Please enter your email"
            !EMAIL_REGEX.matches(email.trim()) -> "Enter a valid email address"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Please enter a password"
            password.length < 8 -> "Password must be at least 8 characters"
            password.none { it.isDigit() } -> "Password must contain at least one number"
            else -> null
        }
    }

    fun validateConfirmPassword(password: String, confirm: String): String? {
        return when {
            confirm.isBlank() -> "Please confirm your password"
            confirm != password -> "Passwords do not match"
            else -> null
        }
    }

    fun validateDonationAmount(rawAmount: String): String? {
        val amount = rawAmount.toDoubleOrNull()
        return when {
            rawAmount.isBlank() -> "Enter an amount"
            amount == null -> "Enter a valid number"
            amount <= 0 -> "Amount must be greater than zero"
            else -> null
        }
    }
}
