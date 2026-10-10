package com.arcadia.aiscompose.View

import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.fillMaxSize
import com.arcadia.aiscompose.Model.Expense
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory

@Composable
fun ExpenseScreen(token: String) {

//    viewModel: TransactionViewModel = viewModel()
//    val data by viewModel.expenseList.collectAsState()
//
//    //val filteredData = data.filter { it.coa_name == "Bebam Bensin" }
//
//    LaunchedEffect(true) {
//        viewModel.fetchExpenseReport()
//    }

    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchExpenseReport()
    }

    val data : List<Expense> = vm2.expenseList


    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {

//        Column(modifier = Modifier
//            .padding(8.dp)) {
//            Spacer(modifier = Modifier.height(32.dp))
//
//            LazyColumn {
//                items(data) { item: Expense ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .padding(16.dp)
//                    ) {
                        ExpenseChartView(expenses = data) // 👈 satu chart per item
//                    }
//                }
//            }
//        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


