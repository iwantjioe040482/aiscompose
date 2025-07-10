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
import androidx.compose.foundation.layout.padding
import com.arcadia.aiscompose.Model.Expense

@Composable
fun DailyExpenseScreen(viewModel: TransactionViewModel = viewModel()) {

    val data by viewModel.dailyexpenseList.collectAsState()


    LaunchedEffect(true) {
        viewModel.fetchDailyExpense()
    }


    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {

        DailyExpenseChartView(data = data) // 👈 satu chart per item
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}



