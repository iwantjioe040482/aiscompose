package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.Assets
import retrofit2.http.*

interface AssetApi {
    @GET("assets")
    suspend fun getAssetList(): List<Assets>

}
