package com.arcadia.aiscompose.View


import androidx.compose.runtime.*
import com.arcadia.aiscompose.Model.Transaction
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.ViewModel.COAViewModel
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import java.time.LocalDate
import androidx.compose.runtime.collectAsState
import com.arcadia.aiscompose.Model.BalanceResponse
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.COAViewModelFactory
import com.arcadia.aiscompose.ViewModel.WaterViewModel
import com.arcadia.aiscompose.Repository.TransactionRepository
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.Repository.WaterViewModelFactory
import androidx.compose.ui.text.style.TextAlign
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.arcadia.aiscompose.Model.COAItem
import java.util.TimeZone
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun COATrackerScreen(token: String) {
    //val vm: COAViewModel = viewModel()
    //val vm2: TransactionViewModel = viewModel()
    val factory2 = remember { COAViewModelFactory(token) }
    val factory = remember { TransactionViewModelFactory(token) }

    val vm2 : TransactionViewModel = viewModel(factory = factory)
    val vm: COAViewModel = viewModel(factory = factory2)

    var transaction by remember { mutableStateOf("Cash") }
    var transactionExpanded by remember { mutableStateOf(false) }

    var selectedCOAId by remember { mutableStateOf<Int?>(null) }
    var coaExpanded by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var priority by remember { mutableStateOf("Keinginan") }
    var priorityExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm.setToken(token)

        vm2.fetchExpense()
        vm.fetchCOAExpense()
        vm.fetchCOATrans()

    }

    LaunchedEffect(transaction) {
        vm2.fetchBalanceByCOA(transaction)
    }


    val balance : List<BalanceResponse> = vm2.balance
    //val balance by vm2.balance.collectAsState()
    //val transactionList by vm2.transactionList.collectAsState()
    val transactionList : List<TransactionView> = vm2.transactionList
    val coaList : List<COAItem> = vm.coatransList
    var showBalance by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "COA Tracker",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth() // <- penting agar bisa center
                .padding(bottom = 1.dp)
        )

        // Transaction Dropdown - Left-Right (1 row, 2 columns)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Transaction",
                modifier = Modifier
                    .weight(1f)
                    .alignByBaseline()
            )

            Box(
                modifier = Modifier
                    .weight(2f)
                    .alignByBaseline()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { transactionExpanded = true }) {
                        Text(transaction)
                    }
                    DropdownMenu(
                        expanded = transactionExpanded,
                        onDismissRequest = { transactionExpanded = false }
                    ) {
                        coaList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.coa_name) },
                                onClick = {
                                    transaction = item.route_name  // Simpan value (route_name)
                                    transactionExpanded = false
                                }
                            )
                        }
                    }


                    val updatedDate: String = balance.firstOrNull()?.updated_at ?: "1900-01-01"!!

                    Text(
                        text = "Last: $updatedDate",
                        //color = Color.Green,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(1.dp))


        val balanceValue = balance.firstOrNull()?.Balance ?: 0.0
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (showBalance)
                    "Balance : Rp ${"%,.2f".format(balanceValue)}"
                else
                    "Balance : *****",
                color = if (showBalance) Color.Green else Color.Unspecified,
                style = MaterialTheme.typography.headlineSmall
            )

            IconButton(onClick = { showBalance = !showBalance }) {
                Icon(
                    imageVector = if (showBalance) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (showBalance) "Sembunyikan" else "Tampilkan"
                )
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        LazyColumn(modifier = Modifier
            .weight(1f)
            .navigationBarsPadding()) {
            items(transactionList.size) { index ->
                TransactionItem(transactionList[index])
            }
        }
    }
}

