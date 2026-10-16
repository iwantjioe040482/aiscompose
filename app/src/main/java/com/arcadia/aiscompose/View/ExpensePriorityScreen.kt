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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.arcadia.aiscompose.Model.PriorityItem


@Composable
fun ExpensePriorityScreen(token: String) {
    var showProgress by remember { mutableStateOf(true) }
    var showNoDataDialog by remember { mutableStateOf(false) }

    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchPriorityReport()
    }


    val data : List<PriorityItem> = vm2.priorityList

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

        Column(modifier = Modifier
            .padding(4.dp)) {
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(data) { item: PriorityItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(8.dp)
                    ) {
                        ExpensePriorityChartView(data = listOf(item)) // 👈 satu chart per item
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


