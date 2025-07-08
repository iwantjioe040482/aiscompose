package com.arcadia.aiscompose.Model

import java.sql.Timestamp

data class Assets
(
    val id: Int,
    val asset_name: String,
    val type_asset: String,
    val name: String,
    val est_value: Double,
    val last_update: String,
    val last_location: String,
    val asset_icon: String
)
