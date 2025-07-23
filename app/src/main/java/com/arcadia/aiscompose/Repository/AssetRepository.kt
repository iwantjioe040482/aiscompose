package com.arcadia.aiscompose.Repository

import com.arcadia.aiscompose.AssetApi
import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.LogoutRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AssetRepository {
    private val api: AssetApi

    init {
        val retrofit = Retrofit.Builder()
            //.baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AssetApi::class.java)
    }

    suspend fun getAssets(token: String): List<Assets> {
        return api.getAssetList(LogoutRequest(token))
    }
}