package com.arcadia.aiscompose.View


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.setValue
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel
import com.arcadia.aiscompose.Model.Electricity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.ViewModel.AssetViewModel

@Composable
fun AssetScreen() {
    val viewModel: AssetViewModel = viewModel()
    val transactionList by viewModel.transactionList.collectAsState()

    // Tampilan untuk portrait
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(32.dp))

        Text("Asset List", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
            items(transactionList.size) { index ->
                AssetItem(transactionList[index])
            }
        }


    }
}