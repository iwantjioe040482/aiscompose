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

@Composable
fun ExpenseTrackerScreen() {
    val vm: COAViewModel = viewModel()
    val vm2: TransactionViewModel = viewModel()

    var transaction by remember { mutableStateOf("Cash") }
    var transactionExpanded by remember { mutableStateOf(false) }

    var selectedCOAId by remember { mutableStateOf<Int?>(null) }
    var coaExpanded by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var priority by remember { mutableStateOf("Keinginan") }
    var priorityExpanded by remember { mutableStateOf(false) }

    val balance by vm2.balance.collectAsState()
    val transactionList by vm2.transactionList.collectAsState()

    vm2.fetchBalanceByCOA(transaction)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Text(
            text = "Expense Tracker",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Transaction Dropdown - Left-Right (1 row, 2 columns)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
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
                TextButton(onClick = { transactionExpanded = true }) {
                    Text(transaction)
                }
                DropdownMenu(
                    expanded = transactionExpanded,
                    onDismissRequest = { transactionExpanded = false }
                ) {
                    listOf("Allo Bank", "BCA", "Cash","CIMB","Dana", "Flazz", "GoPay","Hana","Octo", "Ovo","Permata", "ShopeePay").forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                transaction = it
                                transactionExpanded = false
                                vm2.fetchBalanceByCOA(it)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Balance
        Text(
            text = "Balance: Rp ${"%,.2f".format(balance)}",
            color = Color.Green,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        // COA Dropdown
        //Text("COA")
        Box(modifier = Modifier.fillMaxWidth()) {
            //OutlinedButton(onClick = { coaExpanded = true }, modifier = Modifier.fillMaxWidth()) {
            //    Text(selectedCOAId?.toString() ?: "Pilih COA")
            //}
            COADropdownMenu(
                coaList = vm.coaList.value,
                selectedId = selectedCOAId,
                onSelect = {
                    selectedCOAId = it
                    coaExpanded = false
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Priority Dropdown - Left-Right (1 row, 2 columns)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Priority",
                modifier = Modifier
                    .weight(1f)
                    .alignByBaseline()
            )

            Box(
                modifier = Modifier
                    .weight(2f)
                    .alignByBaseline()
            ) {
                TextButton(onClick = { priorityExpanded = true }) {
                    Text(priority)
                }
                DropdownMenu(
                    expanded = priorityExpanded,
                    onDismissRequest = { priorityExpanded = false }
                ) {
                    listOf("Keinginan", "Kebutuhan").forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                priority = it
                                priorityExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Submit Button
        Button(
            onClick = {
                val value = amount.toDoubleOrNull() ?: 0.0
                val priorityId = if (priority == "Kebutuhan") 2 else 1

                if (value > 0 && selectedCOAId != null) {
                    val newExpense = Transaction(
                        date = LocalDate.now().toString(),
                        coa_id = selectedCOAId!!,
                        total = value,
                        description = description,
                        priority_id = priorityId
                    )

                    vm2.submitExpense(transaction, newExpense,
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

        Spacer(modifier = Modifier.height(8.dp))

        // Expense List
        Text("Daily Expense", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(transactionList.size) { index ->
                TransactionItem(transactionList[index])
            }
        }
    }
}
