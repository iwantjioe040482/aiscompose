package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arcadia.aiscompose.Model.Assets

@Composable
fun AssetItem (e: Assets) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(text = "Asset Name: ${e.asset_name}")
        Text(text = "Type: ${e.type_asset} - By: ${e.name}")
        Divider()
    }
}
