package com.giftrelief.app.data.model

data class User(
    val id: String? = null,
    val fullName: String,
    val email: String,
    val currencyPreference: String = "ZAR"
)
