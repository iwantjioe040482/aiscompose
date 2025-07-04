package com.arcadia.aiscompose.Model

data class Transaction(
    val date: String,
    val coa: String,
    val amount: Double,
    val description: String,
    val priority: String
)
