package com.arcadia.aiscompose.Repository

import com.arcadia.aiscompose.AssetApi
import com.arcadia.aiscompose.ElectricityApi
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.EstElectricBill
import com.arcadia.aiscompose.Model.LogoutRequest
import com.arcadia.aiscompose.TransactionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

object COARepository {
    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
    }


    suspend fun getCOAList(token: String): List<COAItem> {
        return api.getCOAList("Bearer $token")
    }


}