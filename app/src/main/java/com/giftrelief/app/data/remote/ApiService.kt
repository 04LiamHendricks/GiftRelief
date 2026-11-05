package com.giftrelief.app.data.remote

import com.giftrelief.app.data.model.AuthResponse
import com.giftrelief.app.data.model.Donation
import com.giftrelief.app.data.model.DonationRequest
import com.giftrelief.app.data.model.LoginRequest
import com.giftrelief.app.data.model.RegisterRequest
import com.giftrelief.app.data.model.UpdateProfileRequest
import com.giftrelief.app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * REST contract for the hosted GiftRelief API.
 * Swap BASE_URL in RetrofitInstance once the backend is deployed.
 */
interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("donations")
    suspend fun getDonations(@Header("Authorization") bearerToken: String): Response<List<Donation>>

    @POST("donations/{id}/donate")
    suspend fun donate(
        @Header("Authorization") bearerToken: String,
        @Path("id") donationId: String,
        @Body request: DonationRequest
    ): Response<Donation>

    @PUT("users/me")
    suspend fun updateProfile(
        @Header("Authorization") bearerToken: String,
        @Body request: UpdateProfileRequest
    ): Response<User>
}
