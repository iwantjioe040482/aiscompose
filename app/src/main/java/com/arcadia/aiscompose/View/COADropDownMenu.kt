package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import com.arcadia.aiscompose.Model.COAItem
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

@Composable
fun COADropdownMenu(
    coaList: List<COAItem>,
    selectedId: Int?,
    onSelect: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    val selectedName = coaList.find { it.id == selectedId }?.coa_name ?: "Pilih COA"

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = { Text("COA") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            coaList.forEach { coa ->
                DropdownMenuItem(
                    text = { Text(coa.coa_name) },
                    onClick = {
                        onSelect(coa.id)
                        expanded = false
                    }
                )
            }
        }
    }
}