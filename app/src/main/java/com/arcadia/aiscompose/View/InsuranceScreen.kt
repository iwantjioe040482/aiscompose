package com.arcadia.aiscompose.View

import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.InsuranceItem
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory

@Composable
fun InsuranceScreen(token: String) {
    //val data by viewModel.pivotList.collectAsState()

    //viewModel: TransactionViewModel = viewModel()
    //val data by viewModel.insuranceList.collectAsState()

    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

//    LaunchedEffect(true) {
//        viewModel.fetchInsurance()
//    }

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchInsurance()
    }

    val data : List<InsuranceItem> = vm2.insuranceList

    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {
        Column(modifier = Modifier
            .padding(8.dp)) {
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn {
                items(data) { item: InsuranceItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp)
                    ) {
                        InsutanceChartView(data = listOf(item)) // 👈 satu chart per item
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}



