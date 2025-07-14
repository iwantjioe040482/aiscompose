package com.arcadia.aiscompose

import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.LogoutRequest
import retrofit2.http.*

interface AssetApi {
    @POST("assets")
    suspend fun getAssetList(@Body request: LogoutRequest): List<Assets>

}
