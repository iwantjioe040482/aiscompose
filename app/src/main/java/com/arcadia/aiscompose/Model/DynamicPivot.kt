package com.arcadia.aiscompose.Model

data class DynamicPivot(
    val coaName: String,
    val values: Map<String, Float> // key = bulan, value = jumlah
)