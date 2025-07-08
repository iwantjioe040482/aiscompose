package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.arcadia.aiscompose.ViewModel.SidebarViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: SidebarViewModel = viewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItemsState = viewModel.menuItems.collectAsState()
    val selectedItemState = viewModel.selectedItem.collectAsState()
    val menuItems = menuItemsState.value
    val selectedItem = selectedItemState.value
    val scrollState = rememberScrollState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {
                Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                Divider()
                menuItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        selected = selectedItem == item,
                        onClick = {
                            viewModel.selectMenuItem(item)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Arcadia Information System") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            // Content Area
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                if (selectedItem == null) {
                    Text("Memuat data...", style = MaterialTheme.typography.bodyLarge)
                } else {
                    when (selectedItem?.route) {
                        "Home" -> HomeScreen()
                        "WaterInput" -> WaterInputScreen()
                        "ElectricityInput" -> ElectricityInputScreen()
                        "Transaction" -> ExpenseTrackerScreen()
                        "Incomes" -> IncomeTrackerScreen()
                        "Transfer" -> TransferInputForm()
                        "Pivot" -> PivotReportScreen()
                        "Tax" -> TaxScreen()
                        "CreditCard" -> CreditCardScreen()
                        "Insurance" -> InsuranceScreen()
                        "Expense" -> ExpenseScreen()
                        "Assets" -> AssetScreen()
                        else -> Text("Select a menu item")
                    }
                }
            }
        }
    }
}