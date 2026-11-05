package com.giftrelief.app.data.model

// Sent to POST /auth/register
// NOTE: the raw password only ever travels over HTTPS/TLS to the server;
// the server is responsible for salting + hashing it (e.g. bcrypt) before
// it is stored. The app never stores or logs the plaintext password.
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

// Sent to POST /auth/login
data class LoginRequest(
    val email: String,
    val password: String
)

// Returned by both /auth/login and /auth/register
data class AuthResponse(
    val token: String,
    val user: User
)
