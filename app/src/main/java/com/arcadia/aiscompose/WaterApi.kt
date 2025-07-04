package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.EstWaterBill
import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.WaterView
import retrofit2.http.*

interface WaterApi {
    @GET("water")
    suspend fun getWaterList(): List<WaterView>

    @GET("estwaterbill")
    suspend fun getEstWaterBill(): List<EstWaterBill>

    @POST("water")
    suspend fun submitWater(@Body data: Water)
}