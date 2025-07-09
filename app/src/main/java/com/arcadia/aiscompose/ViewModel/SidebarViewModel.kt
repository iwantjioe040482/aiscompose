package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import com.arcadia.aiscompose.Model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SidebarViewModel : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _selectedItem = MutableStateFlow<MenuItem?>(null)
    val selectedItem: StateFlow<MenuItem?> = _selectedItem

    init {
        viewModelScope.launch {
            _menuItems.value =
                listOf(
                    MenuItem(route = "Home", label = "Home", id = "Home"),
                    MenuItem(route = "WaterInput", label = "Water", id = "WaterInput"),
                    MenuItem(route = "ElectricityInput", label = "Electricity", id = "ElectricityInput"),
                    MenuItem(route = "Transaction", label = "Expense", id = "Transaction"),
                    MenuItem(route = "Incomes", label = "Incomes", id = "Incomes"),
                    MenuItem(route = "Transfer", label = "Transfer", id = "Transfer"),

                    // Report Group
                    MenuItem(
                        label = "Report",
                        id = "Report",
                        children = listOf(
                            MenuItem(route = "Pivot", label = "Expense Monthly Pivot", id = "Pivot"),
                            MenuItem(route = "Expense", label = "Expense Monthly", id = "Expense"),
                            MenuItem(route = "Tax", label = "Tax", id = "Tax"),
                            MenuItem(route = "CreditCard", label = "Credit Card", id = "CreditCard"),
                            MenuItem(route = "Insurance", label = "Insurance", id = "Insurance")
                        )
                    ),

                    MenuItem(route = "Assets", label = "Assets", id = "Assets")
                )
//                listOf(
//                MenuItem(route = "Home", label = "Home", id = "Home"),
//                MenuItem(route = "WaterInput", label = "Water Input", id = "WaterInput"),
//                MenuItem(route = "ElectricityInput", label = "Electricity Input", id =",ElectricityInput" ),
//                MenuItem(route = "Transaction", label = "Expense Input", id ="Transaction" ),
//                MenuItem(route = "Incomes", label = "Incomes Input", id ="Incomes" ),
//                MenuItem(route = "Transfer", label = "Transfer Input", id ="Transfer" ),
//                MenuItem(route = "Pivot", label = "Expense Monthly Report", id ="Pivot" ),
//                MenuItem(route = "Tax", label = "Tax Report", id ="Tax" ),
//                MenuItem(route = "CreditCard", label = "Credit Card Report", id ="CreditCard" ),
//                MenuItem(route = "Insurance", label = "Insurance Report", id ="Insurance" ),
//                MenuItem(route = "Expense", label = "Expense Report", id ="Expense" ) ,
//                MenuItem(route = "Assets", label = "Assets", id ="Assets" )
//
//            )

            _selectedItem.value = _menuItems.value.firstOrNull()
        }
    }

    fun selectMenuItem(item: MenuItem) {
        _selectedItem.value = item
    }
}