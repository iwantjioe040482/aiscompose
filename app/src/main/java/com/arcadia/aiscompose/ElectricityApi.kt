package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.EstElectricBill
import retrofit2.http.*

interface ElectricityApi {
    @GET("electricity")
    suspend fun getElectricityList(): List<ElectricityView>

    @GET("estelectricbill")
    suspend fun getEstElectricBill(): List<EstElectricBill>

    @POST("electricity")
    suspend fun submitElectricity(@Body data: Electricity)
}