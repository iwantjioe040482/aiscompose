package com.arcadia.aiscompose.Model

data class MenuItem (
//    val id: String,
//    val label: String,
//    val route: String
    val route: String? = null,
    val label: String,
    val id: String,
    val children: List<MenuItem>? = null // Tambahkan untuk nested menu
)