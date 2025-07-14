package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.EstElectricBill
import retrofit2.http.*

interface ElectricityApi {
    @GET("electricity")
    suspend fun getElectricityList(@Header("Authorization") token: String): List<ElectricityView>

    @GET("estelectricbill")
    suspend fun getEstElectricBill(@Header("Authorization") token: String): List<EstElectricBill>

    @POST("electricity")
    suspend fun submitElectricity(@Header("Authorization") token: String,@Body data: Electricity)
}