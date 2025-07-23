package com.arcadia.aiscompose.Repository


import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.EstWaterBill
import com.arcadia.aiscompose.Model.LogoutRequest
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.WaterApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WaterRepository {
    private val api: WaterApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(WaterApi::class.java)
    }

    suspend fun getWater(token: String): List<WaterView> {
        return api.getWaterList("Bearer $token")
    }

    suspend fun getWaterBalance(token: String): List<EstWaterBill> {
        return api.getEstWaterBill("Bearer $token")
    }

    suspend fun submitWater(token: String,water: Water) {
        return api.submitWater("Bearer $token",water,)
    }


}