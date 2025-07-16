package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import com.arcadia.aiscompose.ViewModel.PivotMonthlyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.DynamicPivot
import com.arcadia.aiscompose.Repository.PivotMonthlyViewModelFactory
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import com.arcadia.aiscompose.View.DynamicPivotTableScreen
import android.util.Log

@Composable
fun PivotReportScreen(token: String) {
    //val data by viewModel.pivotList.collectAsState()
//    viewModel: PivotMonthlyViewModel = viewModel()
//    val data by viewModel.dynamiclist.collectAsState()
//
//    LaunchedEffect(true) {
//        viewModel.fetchPivot()
//    }
    val factory = remember { PivotMonthlyViewModelFactory(token) }
    val vm2 : PivotMonthlyViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchPivot()
    }

    val data : List<DynamicPivot> = vm2.dynamiclist

    Log.d("PivotReport", "Expenses size: ${data.size}")

    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {
        DynamicPivotTableScreen(data = data)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
