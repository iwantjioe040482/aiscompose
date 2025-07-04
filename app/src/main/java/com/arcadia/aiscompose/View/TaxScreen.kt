package com.arcadia.aiscompose.View

import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import androidx.compose.runtime.*
import com.arcadia.aiscompose.ViewModel.PivotMonthlyViewModel
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
import com.arcadia.aiscompose.Model.TaxItem

@Composable
fun TaxScreen(viewModel: TransactionViewModel = viewModel()) {
    //val data by viewModel.pivotList.collectAsState()
    val data by viewModel.taxList.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchTax()
    }

    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {
        //Box(modifier = Modifier
        //    .fillMaxWidth()
        //    .height(300.dp)
        //) {
        //    Column(modifier = Modifier.fillMaxSize()) {
        //        Spacer(modifier = Modifier.height(64.dp)) // 👉 Menurunkan PieChart ke bawah

        //        TaxChartView(data = data)
        //    }

        //}
        Column(modifier = Modifier
            .padding(8.dp)) {
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn {
                items(data) { item: TaxItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp)
                    ) {
                        TaxChartView(data = listOf(item)) // 👈 satu chart per item
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

