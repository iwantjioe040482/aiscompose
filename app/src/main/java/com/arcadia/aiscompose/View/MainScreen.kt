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
import com.arcadia.aiscompose.Model.MenuItem
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: SidebarViewModel = viewModel()) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val menuItems by viewModel.menuItems.collectAsState()
//    val selectedItem by viewModel.selectedItem.collectAsState()
//    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }
//
//    LazyColumn(modifier = Modifier.padding(16.dp)) {
//        items(menuItems) { item ->
//            MenuItemView(
//                item = item,
//                selectedItem = selectedItem,
//                onClick = { viewModel.selectMenuItem(it) },
//                expandedMap = expandedMap
//            )
//        }
//    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

//    val menuItemsState = viewModel.menuItems.collectAsState()
//    val selectedItemState = viewModel.selectedItem.collectAsState()
    val menuItems by viewModel.menuItems.collectAsState()
    val selectedItem by viewModel.selectedItem.collectAsState()
    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }
//    val menuItems = menuItemsState.value
//    val selectedItem = selectedItemState.value
    val scrollState = rememberScrollState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    //.verticalScroll(scrollState)
                    .padding(8.dp)
            ) {
                Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                Divider()
//                menuItems.forEach { item ->
//                    NavigationDrawerItem(
//                        label = { Text(item.label) },
//                        selected = selectedItem == item,
//                        onClick = {
//                            viewModel.selectMenuItem(item)
//                            scope.launch { drawerState.close() }
//                        },
//                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
                LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                    items(items = menuItems) { item ->
                        MenuItemView(
                            item = item,
                            selectedItem = selectedItem,
                            onClick = {
                                viewModel.selectMenuItem(it)
                                scope.launch { drawerState.close() }
                            },
                            expandedMap = expandedMap
                        )
                    }
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

@Composable
fun MenuItemView(
    item: MenuItem,
    selectedItem: MenuItem?,
    onClick: (MenuItem) -> Unit,
    expandedMap: MutableMap<String, Boolean>,
    indent: Dp = 0.dp
) {
    val isSelected = item == selectedItem
    val isExpandable = item.children != null

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = indent, top = 8.dp, bottom = 8.dp)
                .clickable {
                    if (isExpandable) {
                        expandedMap[item.id] = !(expandedMap[item.id] ?: false)
                    } else {
                        onClick(item)
                    }
                }
        ) {
            Text(
                text = item.label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
            )
        }

        // Tampilkan children jika expand
        if (isExpandable && (expandedMap[item.id] ?: false)) {
            item.children?.forEach { child ->
                MenuItemView(
                    item = child,
                    selectedItem = selectedItem,
                    onClick = onClick,
                    expandedMap = expandedMap,
                    indent = indent + 16.dp
                )
            }
        }
    }
}