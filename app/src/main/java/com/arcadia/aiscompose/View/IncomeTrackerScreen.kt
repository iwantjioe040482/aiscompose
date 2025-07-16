package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import com.arcadia.aiscompose.Model.Transaction
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.ViewModel.COAIncomeViewModel
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import java.time.LocalDate
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.arcadia.aiscompose.Repository.COAIncomeViewModelFactory
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.ViewModel.COAViewModel
import androidx.compose.ui.text.style.TextAlign


@Composable
fun IncomeTrackerScreen(token: String) {
    var selectedCOAId by remember { mutableStateOf<Int?>(null) }
    var coaExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    //val vm: COAIncomeViewModel = viewModel()
 // val vm2: TransactionViewModel = viewModel()
    val factory2 = remember { COAIncomeViewModelFactory(token) }
    val vm: COAIncomeViewModel = viewModel(factory = factory2)


    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

//    val data by vm.coaList.collectAsState()

    var transaction by remember { mutableStateOf("Cash") }
    var transactionExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm.setToken(token)
        vm.fetchCOAIncomes()
    }

    LaunchedEffect(transaction) {
        vm2.fetchBalanceByCOA(transaction)
    }

    //val balance by vm2.balance.collectAsState()
    val balance : List<Double> = vm2.balance
    val data = vm.coaList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Income Tracker",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth() // <- penting agar bisa center
                .padding(bottom = 2.dp)
        )

        // COA Dropdown
        COADropdownMenu(
            coaList = data,
            selectedId = selectedCOAId,
            onSelect = {
                selectedCOAId = it
                coaExpanded = true
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(2.dp))

        // ⬇️ Transaction in 1 Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transaction",
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.weight(2f)) {
                TextButton(onClick = { transactionExpanded = true }) {
                    Text(transaction)
                }
                DropdownMenu(
                    expanded = transactionExpanded,
                    onDismissRequest = { transactionExpanded = false }
                ) {
                    listOf(
                        "Allo Bank", "BCA","Blu", "Cash", "CIMB", "Dana", "Flazz",
                        "GoPay", "Hana","Mandiri", "Octo", "Ovo", "Permata", "ShopeePay"
                    ).forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                transaction = it
                                transactionExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        val balanceValue = balance.firstOrNull() ?: 0.0

        Text(
            text = "Balance: Rp ${"%,.2f".format(balanceValue)}",
            color = Color.Green,
            style = MaterialTheme.typography.headlineMedium
        )
//        // Balance
//        Text(
//            text = "Balance: Rp ${"%,.2f".format(balance)}",
//            color = Color.Green,
//            style = MaterialTheme.typography.headlineMedium
//        )

        Spacer(modifier = Modifier.height(2.dp))

        // Submit Button
        Button(
            onClick = {
                val value = amount.toDoubleOrNull() ?: 0.0
                if (value > 0 && selectedCOAId != null) {
                    val newExpense = Transaction(
                        date = LocalDate.now().toString(),
                        coa_id = selectedCOAId!!,
                        total = value,
                        description = description,
                        priority_id = 1
                    )
                    vm2.submitIncome(transaction, newExpense,
                        onSuccess = {
                            vm2.fetchBalanceByCOA(transaction)
                            vm2.fetchExpense()
                            amount = ""
                            description = ""
                        },
                        onError = {
                            println("Error submit: ${it.message}")
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}
