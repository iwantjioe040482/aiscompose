package com.arcadia.aiscompose.Model
import java.util.Date

data class MaintenanceItem (
    val id: Int,
    val asset_name: String,
    val maintenance_name: String,
    val end_to: String,
    val remaining: Int,
    val timeline: Int,
    val username:String,
    val legend_uom:String,
)