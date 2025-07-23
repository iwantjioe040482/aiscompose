package com.arcadia.aiscompose

import retrofit2.http.*
import retrofit2.Response
import com.arcadia.aiscompose.Model.LoginRequest
import com.arcadia.aiscompose.Model.LoginResponse
import com.arcadia.aiscompose.Model.LogoutRequest
import com.arcadia.aiscompose.Model.RegisterRequest

interface AuthApi {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest):Response <LoginResponse>

    @POST("/logout")
    suspend fun logout(@Body request: LogoutRequest)

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>
}