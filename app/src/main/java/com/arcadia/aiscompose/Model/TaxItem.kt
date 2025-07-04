package com.arcadia.aiscompose.Model

import java.util.Date

data class TaxItem (
    val id: Int,
    val name: String,
    val end_to: Date,
    val remaining: Int,
    val timeline: Int,
    val username:String
)
