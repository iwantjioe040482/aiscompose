package com.arcadia.aiscompose.Model

data class TransferRequest(
    val from: String,
    val to: String,
    val amount: Double
)

data class TransferResponse(
    val message: String,
    val data: TransferRequest
)
