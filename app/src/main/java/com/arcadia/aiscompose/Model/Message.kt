package com.arcadia.aiscompose.Model

data class Message(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val senderId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isMine: Boolean = false
)

