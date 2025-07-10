package com.arcadia.aiscompose

import retrofit2.http.*
import retrofit2.Response
import com.arcadia.aiscompose.Model.LoginRequest
import com.arcadia.aiscompose.Model.LoginResponse
import com.arcadia.aiscompose.Model.LogoutRequest

interface AuthApi {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest):Response <LoginResponse>

    @POST("/logout")
    suspend fun logout(@Body request: LogoutRequest)
}