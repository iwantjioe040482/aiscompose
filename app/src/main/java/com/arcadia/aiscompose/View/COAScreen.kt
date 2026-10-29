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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

@Composable
fun COAScreen(token: String) {
    val factory = remember(token) {
        TransactionViewModelFactory(token)
    }

    val vm2: TransactionViewModel = viewModel(
        factory = factory
    )

    val originalItems by vm2.coaaccessList.collectAsState()

    var coaItems by remember {
        mutableStateOf<List<COAAccess>>(emptyList())
    }

    LaunchedEffect(token) {
        coaItems = emptyList()
        vm2.setToken(token)
        vm2.fetchCOAAccess()
    }

    LaunchedEffect(originalItems) {
        coaItems = originalItems
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                color = if (isSystemInDarkTheme()) {
                    Color.Black// Abu-abu gelap
                } else {
                    Color.White       // Putih
                },
                contentColor = MaterialTheme.colorScheme.onSurface,
                shadowElevation = 4.dp
            ) {
                Button(
                    onClick = {
                        val selected = coaItems.filter {
                            it.is_checked == 1
                        }

                        val postData = selected.map {
                            CoaUserPostDTO(it.coa_id)
                        }

                        vm2.submitCOAAccess(postData)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp
                        )
                ) {
                    Text("Simpan")
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (coaItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data COA.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                COACheckboxGroupedList(
                    token = token,
                    items = coaItems,
                    onCheckedChange = { updatedItem ->
                        coaItems = coaItems.map { item ->
                            if (
                                item.combined_label ==
                                updatedItem.combined_label
                            ) {
                                updatedItem
                            } else {
                                item
                            }
                        }
                    }
                )
            }
        }
    }
}
//@Composable
//fun COAScreen(token: String) {
////    val factory = remember { TransactionViewModelFactory(token) }
//    val factory = remember(token) {
//        TransactionViewModelFactory(token)
//    }
//
//    val vm2: TransactionViewModel = viewModel(factory = factory)
//
//    val originalItems by vm2.coaaccessList.collectAsState()
//    //var coaItems by remember { mutableStateOf<List<COAAccess>>(emptyList()) }
////    var coaItems by remember(originalItems) { mutableStateOf(originalItems) }
//
//    var coaItems by remember {
//        mutableStateOf<List<COAAccess>>(emptyList())
//    }
//
//    LaunchedEffect(token) {
//
//        // Clear existing list to avoid cache
//        coaItems = emptyList()
//        vm2.setToken(token)
//        vm2.fetchCOAAccess()
//    }
//
//    LaunchedEffect(originalItems) {
//        if (coaItems.isEmpty()) {
//            coaItems = originalItems
//        }
////        coaItems = originalItems
////        Log.d("COAAccess", "originalItems size: ${originalItems.size}")
//    }
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        contentWindowInsets = WindowInsets.safeDrawing,
//        bottomBar = {
//            Surface(
//                tonalElevation = 3.dp
//            ) {
//                Button(
//                    onClick = {
//                        val selected = coaItems.filter { it.is_checked == 1 }
//                        Log.d("COAScreen", "Selected: $selected")
//                        val postData = selected.map { CoaUserPostDTO(it.coa_id) }
//                        vm2.submitCOAAccess(postData)
//                        //coaItems= emptyList()
//                        //vm2.fetchCOAAccess()
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(2.dp)
//                ) {
//                    Text("Simpan")
//                }
//            }
//        }
//    ) { paddingValues ->
//    Column (
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//    ){
//        Spacer(modifier = Modifier.height(16.dp))
//        if (coaItems.isEmpty()) {
//            Text(
//                text = "Tidak ada data COA.",
//                modifier = Modifier.padding(16.dp),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        } else {
//            COACheckboxGroupedList(token,coaItems) { updatedItem ->
//                coaItems = coaItems.map { item ->
//                    if (item.combined_label == updatedItem.combined_label) updatedItem else item
//                }
//            }
//        }
//
//    }
//    }
//}
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

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentPadding = PaddingValues(
//            top = 8.dp,
//            bottom = 8.dp
//        )
//    ) {
//        groupedItems.forEach { (prefix, group) ->
//            val sortedGroup = group.sortedBy { it.is_checked } // unchecked (0) first
//            item {
//                Text(
//                    text = categoryLabels[prefix] ?: "Kategori $prefix",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//            }
//
//            items(sortedGroup,
//                key = { it.coa_id }) { item ->// use sortedGroup here
//                //var editedLabel by remember { mutableStateOf(item.combined_label) }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp)
//                ) {
//                    Checkbox(
//                        checked = item.is_checked == 1,
//                        onCheckedChange = { isChecked ->
//                            onCheckedChange(item.copy(is_checked = if (isChecked) 1 else 0))
//                        }
//                    )
//                    Text(
//                        text = item.combined_label,
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(start = 8.dp)
//                    )
////                    OutlinedTextField(
////                        value = editedLabel,
////                        onValueChange = { newText ->
////                            editedLabel = newText
////                            onCheckedChange(item.copy(combined_label = newText))
////                        },
////                        modifier = Modifier
////                            .weight(1f)
////                            .padding(start = 8.dp),
////                        textStyle = MaterialTheme.typography.bodyLarge,
////                        singleLine = true
////                    )
//                }
//            }
//
//            item {
//                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
//            }
//        }
//    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 8.dp
        )
    ) {
        groupedItems.forEach { (prefix, group) ->

            val sortedGroup = group.sortedBy {
                it.is_checked
            }

            item {
                Text(
                    text = categoryLabels[prefix]
                        ?: "Kategori $prefix",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    )
                )
            }

            items(
                items = sortedGroup,
                key = { it.coa_id }
            ) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.is_checked == 1,
                        onCheckedChange = { isChecked ->
                            onCheckedChange(
                                item.copy(
                                    is_checked = if (isChecked) 1 else 0
                                )
                            )
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
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}