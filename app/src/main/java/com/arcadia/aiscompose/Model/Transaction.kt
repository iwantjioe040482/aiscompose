package com.arcadia.aiscompose.Model

data class Transaction(
    val date: String,
    val coa_id: Int,
    val total: Double,
    val description: String,
    val priority_id: Int
)
