package com.arcadia.aiscompose.View

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.arcadia.aiscompose.Model.MenuItem

@Composable
fun DrawerItem(item: MenuItem, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(item.label) },
        selected = false,
        onClick = onClick
    )
}