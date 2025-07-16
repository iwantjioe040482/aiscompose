package com.arcadia.aiscompose.View

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.arcadia.aiscompose.ViewModel.LoginViewModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.saveable.rememberSaveable

//import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(tokenState: MutableState<String?>,  onLogout: () -> Unit,viewModel: SidebarViewModel = viewModel()) {

    val token = tokenState.value ?: return // Jangan render apapun jika token null (sudah logout)
    Log.d("AssetViewModel", "Calling mainscreen with token: $token")

    val isDarkTheme = isSystemInDarkTheme()
    //var isLoggedIn by remember { mutableStateOf(true) } // ✅ kontrol tampilan Login/Main
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
//    if (!isLoggedIn) {
//        LoginScreen() // ✅ hanya tampilkan Login
//    } else {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //val legendColor = if (isDarkTheme) Color.White else Color.Black


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
//                        ,
//                        drawerContainerColor = legendColor // ← tambahkan ini untuk mengubah background
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
                            onLogout = {
                                viewModel.selectMenuItem(menuItems.firstOrNull())
                                //viewModel.selectMenuItem(null) // ✅ optional: clear selected menu
                                onLogout() // ✅ call back to parent
//                                isLoggedIn = false // ✅ keluar dari main layout
//                                // Langsung atur ke Login tanpa tergantung selectedItem
//                                viewModel.selectMenuItem(item.copy(route = "Login")) // atau kosongkan selectedItem
                            },
                            expandedMap = expandedMap,
                            tokenState = tokenState // ✅ Tambahkan ini
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
                        "Home" -> HomeScreen(token)
                        "WaterInput" -> WaterInputScreen(token)
                        "ElectricityInput" -> ElectricityInputScreen(token)
                        "Transaction" -> ExpenseTrackerScreen(token)
                        "Incomes" -> IncomeTrackerScreen(token)
                        "Transfer" -> TransferInputForm(token)
                        "Pivot" -> PivotReportScreen(token)
                        "Tax" -> TaxScreen(token)
                        "CreditCard" -> CreditCardScreen(token)
                        "Insurance" -> InsuranceScreen(token)
                        "Expense" -> ExpenseScreen(token)
                        "Daily" -> DailyExpenseScreen(token)
                        "Assets" -> AssetScreen(token)
//                        "Logout" -> {
//                            // Bisa clear session, token, dsb di sini
//                            LoginScreen(isLoggedIn = false)
//                        }
                        else -> Text("Select a menu item")
                    }
                }
            }
        }
    }
//        }
}

@Composable
fun MenuItemView(
    item: MenuItem,
    selectedItem: MenuItem?,
    onClick: (MenuItem) -> Unit,
    expandedMap: MutableMap<String, Boolean>,
    onLogout: () -> Unit = {},
    indent: Dp = 0.dp,
    //token: String // ⬅️ Tambah ini
    tokenState: MutableState<String?>
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: LoginViewModel = viewModel()
    val isSelected = item == selectedItem
    val isExpandable = item.children != null

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = indent, top = 8.dp, bottom = 8.dp)
                .clickable {
                    when {
                        isExpandable -> {
                            expandedMap[item.id] = !(expandedMap[item.id] ?: false)
                        }
                        item.route == "Logout" -> {
                            //val token = viewModel.loginResult.value?.token
                            val token = tokenState.value

                            if (!token.isNullOrBlank()) {
                                coroutineScope.launch {
                                    viewModel.logout(token)
                                    tokenState.value = null // 🔥 Token dihapus
                                    onLogout()
                                }
                            } else {
                                coroutineScope.launch {
                                    tokenState.value = null // Tambahan jaga-jaga
                                    onLogout()
                                }
//                                println("⚠️ Token tidak ditemukan.")
                            }
                        }
                        else -> {
                            onClick(item)
                        }
                    }
                }
        ) {
            Text(
                text = item.label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                fontSize = 18.sp // Ukuran font diperbesar
            )
        }

        // Tampilkan children jika expand
        if (isExpandable && (expandedMap[item.id] ?: false)) {
            item.children?.forEach { child ->
                MenuItemView(
                    item = child,
                    selectedItem = selectedItem,
                    onClick = onClick,
                    onLogout = onLogout,
                    expandedMap = expandedMap,
                    indent = indent + 16.dp,
                    tokenState = tokenState // ⬅️ Kirim token
                )
            }
        }
    }
}