package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import com.arcadia.aiscompose.ViewModel.PivotMonthlyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun PivotReportScreen(viewModel: PivotMonthlyViewModel = viewModel()) {
    //val data by viewModel.pivotList.collectAsState()
    val data by viewModel.dynamiclist.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchPivot()
    }

    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {
        DynamicPivotTableScreen(data = data)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
