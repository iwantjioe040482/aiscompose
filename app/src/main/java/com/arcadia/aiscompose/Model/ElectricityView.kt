package com.arcadia.aiscompose.Model

data class ElectricityView (
    val id: Int,
    val date: String,
    val electricity_value: Double,
    val difference: Double
)
