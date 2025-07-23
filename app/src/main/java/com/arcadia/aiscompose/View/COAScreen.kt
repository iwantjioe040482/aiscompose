package com.arcadia.aiscompose.View

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.Model.COAAccess
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import com.arcadia.aiscompose.Model.CoaUserPostDTO

@Composable
fun COAScreen(token: String) {
    val factory = remember { TransactionViewModelFactory(token) }
    val vm2: TransactionViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        vm2.setToken(token)
        vm2.fetchCOAAccess()
    }

    val originalItems by vm2.coaaccessList.collectAsState()
    var coaItems by remember { mutableStateOf<List<COAAccess>>(emptyList()) }

    LaunchedEffect(originalItems) {
        coaItems = originalItems
        Log.d("COAAccess", "originalItems size: ${originalItems.size}")
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    val selected = coaItems.filter { it.is_checked == 1 }
                    Log.d("COAScreen", "Selected: $selected")
                    val postData = selected.map { CoaUserPostDTO(it.coa_id) }
                    vm2.submitCOAAccess(postData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text("Simpan")
            }
        }
    ) { paddingValues ->
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        if (coaItems.isEmpty()) {
            Text(
                text = "Tidak ada data COA.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            COACheckboxGroupedList(token,coaItems) { updatedItem ->
                coaItems = coaItems.map { item ->
                    if (item.combined_label == updatedItem.combined_label) updatedItem else item
                }
            }
        }

    }
    }
}
@Composable
fun COACheckboxGroupedList(
    token: String,
    items: List<COAAccess>,
    onCheckedChange: (COAAccess) -> Unit
) {
    val groupedItems = items.groupBy { it.coa_prefix }

    val categoryLabels = mapOf(
        "100" to "Bank / Aset",
        "200" to "Pendapatan",
        "500" to "Beban",
        "600" to "Lainnya"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        groupedItems.forEach { (prefix, group) ->
            item {
                Text(
                    text = categoryLabels[prefix] ?: "Kategori $prefix",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(group) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = item.is_checked == 1,
                        onCheckedChange = { isChecked ->
                            onCheckedChange(item.copy(is_checked = if (isChecked) 1 else 0))
                        }
                    )
                    Text(
                        text = item.combined_label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

}