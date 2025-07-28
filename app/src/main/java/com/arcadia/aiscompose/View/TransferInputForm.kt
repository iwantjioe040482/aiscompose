package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Model.TaxItem
import com.arcadia.aiscompose.Model.TransferResponse
import com.arcadia.aiscompose.Repository.COAIncomeViewModelFactory
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.ViewModel.COAIncomeViewModel
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
//import androidx.compose.ui.text.input.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferInputForm(token: String) {
//    val viewModel: TransactionViewModel = viewModel()
    val paymentOptions = listOf("BCA","Blue", "CIMB", "Cash","Flazz","GoPay","Hana","HSBC","Mandiri","Octo", "Ovo", "ShopeePay")

    var source by remember { mutableStateOf(paymentOptions.first()) }
    var destination by remember { mutableStateOf(paymentOptions.last()) }
    var amountText by remember { mutableStateOf("") }

    var expandedSource by remember { mutableStateOf(false) }
    var expandedDestination by remember { mutableStateOf(false) }

//    val transferResult by viewModel.transferResult.collectAsState()
    val factory = remember { TransactionViewModelFactory(token) }
    val vm2 : TransactionViewModel = viewModel(factory = factory)

    val factory2 = remember { COAIncomeViewModelFactory(token) }
    val vm: COAIncomeViewModel = viewModel(factory = factory2)

    val transferResult by vm2.transferResult.collectAsState()

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm.setToken(token)
        vm.fetchCOATrans()
    }

    //val transferResult : List<TransferResponse?> = vm2.transferResult
    //val transferResult by viewModel.transferResult
    val coaList : List<COAItem> = vm.coatransList
    //val coaListto : List<COAItem> = vm.coatransList

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // Source Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedSource,
            onExpandedChange = { expandedSource = !expandedSource }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = source,
                onValueChange = {},
                label = { Text("Dari") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSource) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedSource,
                onDismissRequest = { expandedSource = false }
            ) {
                coaList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.coa_name) },
                        onClick = {
                            source = item.route_name  // Simpan value (route_name)
                            expandedSource = false
                        }
                    )
                }
//                paymentOptions.forEach {
//                    DropdownMenuItem(
//                        text = { Text(it) },
//                        onClick = {
//                            source = it
//                            expandedSource = false
//                        }
//                    )
//                }
            }
        }

        // Destination Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedDestination,
            onExpandedChange = { expandedDestination = !expandedDestination }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = destination,
                onValueChange = {},
                label = { Text("Ke") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDestination) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedDestination,
                onDismissRequest = { expandedDestination = false }
            ) {
                coaList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.coa_name) },
                        onClick = {
                            destination = item.route_name  // Simpan value (route_name)
                            expandedDestination = false
                        }
                    )
                }
//                paymentOptions.forEach {
//                    DropdownMenuItem(
//                        text = { Text(it) },
//                        onClick = {
//                            destination = it
//                            expandedDestination = false
//                        }
//                    )
//                }
            }
        }

        // Amount Input
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text("Jumlah") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Submit Button
        Button(
            onClick = {
                vm2.submitTransfer(source ,destination , amountText.toDoubleOrNull() ?: 0.0)
//                isSending = true
//                result = null
//                val transferRequest = TransferRequest(from, to, amount.toIntOrNull() ?: 0)

//                // Jalankan di coroutine scope
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        val response = ApiClient.api.postTransfer(transferRequest)
//                        withContext(Dispatchers.Main) {
//                            isSending = false
//                            result = if (response.isSuccessful) "Success" else "Failed"
//                        }
//                    } catch (e: Exception) {
//                        withContext(Dispatchers.Main) {
//                            isSending = false
//                            result = "Error: ${e.message}"
//                        }
//                    }
//                }
            },
            //enabled = !isSending,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }


        Column(modifier = Modifier.padding(16.dp)) {
            // Form submit transfer (dari sebelumnya)

            transferResult.firstOrNull()?.let { result ->
                Spacer(modifier = Modifier.height(16.dp))
                Text("Message: ${result.message}")
                Text("From: ${result.data.from}")
                Text("To: ${result.data.to}")
                Text("Amount: ${result.data.amount}")
            }
        }
    }
}