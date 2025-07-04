package com.arcadia.aiscompose.View
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import com.arcadia.aiscompose.Model.ElectricityView

@Composable
fun ElectricityItem(e: ElectricityView) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(text = "Date: ${e.date}")
        Text(text = "Electricity Value: ${e.electricity_value} - Usage: ${e.difference}")
        Divider()
    }
}