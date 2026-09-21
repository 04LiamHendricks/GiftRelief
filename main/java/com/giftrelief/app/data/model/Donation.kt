package com.giftrelief.app.data.model

data class Donation(
    val id: String,
    val title: String,
    val description: String,
    val amountGoal: Double,
    val amountRaised: Double,
    val currency: String = "ZAR"
)

data class DonationRequest(
    val amount: Double,
    val currency: String,
    val anonymous: Boolean = false,
    val recurring: Boolean = false
)
