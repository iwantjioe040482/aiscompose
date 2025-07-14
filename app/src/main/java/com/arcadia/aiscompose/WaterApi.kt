package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.EstWaterBill
import com.arcadia.aiscompose.Model.LogoutRequest
import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.WaterView
import retrofit2.http.*

interface WaterApi {
    @GET("water")
    suspend fun getWaterList(@Header("Authorization") token: String): List<WaterView>

    @GET("estwaterbill")
    suspend fun getEstWaterBill(@Header("Authorization") token: String): List<EstWaterBill>

    @POST("water")
    suspend fun submitWater(@Header("Authorization") token: String,@Body data: Water)
}