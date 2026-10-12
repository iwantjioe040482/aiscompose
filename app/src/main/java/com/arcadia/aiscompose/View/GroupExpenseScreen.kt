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
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.arcadia.aiscompose.Model.Expense
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun GroupExpenseScreen(token: String) {
    var showProgress by remember { mutableStateOf(true) }
    var showNoDataDialog by remember { mutableStateOf(false) }

    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchGroupExpense()
    }


    val data : List<Expense> = vm2.groupexpenseList
//    val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
//    val datafilter: List<Expense> = vm2.groupexpenseList.filter { it.gl_month == currentMonth }

    // Jalankan delay 3 detik jika data kosong
    LaunchedEffect(key1 = data) {
        delay(3000L) // 3 detik
        if (data.isEmpty()) {
            showProgress = false
            showNoDataDialog = true
        }
    }

    // Tampilkan layar hanya jika data sudah diisi
    if (data.isNotEmpty()) {

        Column( modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

//            LazyColumn {
//                items(datafilter) { item: Expense ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp)
//                            .padding(16.dp)
//                    ) {
//                        GroupExpensePieChart(data = listOf(item)) // 👈 satu chart per item
//                    }
//                }
//            }


//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(400.dp)
//                    .padding(16.dp)
//            ) {
//                GroupExpensePieChart(data = datafilter) // 👈 satu chart untuk semua coa ✅
//            }

            val groupedData = data.groupBy { it.gl_month } .toSortedMap(reverseOrder()) // descending

            LazyColumn {
                groupedData.forEach { (month, expenses) ->
                    item {
                        Text("Bulan: $month")
                        GroupExpensePieChart(data = expenses)
                    }
                }
            }
        }
    } else if (showProgress) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    // Dialog pop-up jika tidak ada data setelah 3 detik
    if (showNoDataDialog) {
        AlertDialog(
            onDismissRequest = { showNoDataDialog = false
                showProgress = false // 👈 tambahkan ini
            },
            confirmButton = {
                TextButton(onClick = { showNoDataDialog = false
                    showProgress = false // 👈 tambahkan ini
                }) {
                    Text("OK")
                }
            },
            title = { Text("Tidak Ada Data") },
            text = { Text("Data tidak ditemukan atau masih kosong.") }
        )
    }
}


