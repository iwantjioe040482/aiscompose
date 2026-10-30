package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import com.arcadia.aiscompose.ViewModel.PivotMonthlyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import com.arcadia.aiscompose.Model.CashFlow
import com.arcadia.aiscompose.Model.DynamicPivot
import com.arcadia.aiscompose.Model.MonthlyPivot
import com.arcadia.aiscompose.Repository.PivotMonthlyViewModelFactory

@Composable
fun CashflowReportScreen(token: String) {
    val factory = remember { PivotMonthlyViewModelFactory(token) }
    val vm2 : PivotMonthlyViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchCashFlow()
    }

    val data : List<DynamicPivot> = vm2.dynamiccashflowlist


    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {
        DynamicPivotTableScreen(data = data)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}