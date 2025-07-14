package com.arcadia.aiscompose.Repository

import com.arcadia.aiscompose.AssetApi
import com.arcadia.aiscompose.ElectricityApi
import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.EstElectricBill
import com.arcadia.aiscompose.Model.LogoutRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ElectricityRepository {
    private val api: ElectricityApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ElectricityApi::class.java)
    }

    suspend fun getElectricity(token: String): List<ElectricityView> {
        return api.getElectricityList("Bearer $token")
    }

    suspend fun getElectricityBalance(token: String): List<EstElectricBill> {
        return api.getEstElectricBill("Bearer $token")
    }

    suspend fun submitElectricity(token: String,electricity: Electricity) {
        return api.submitElectricity("Bearer $token",electricity,)
    }

}