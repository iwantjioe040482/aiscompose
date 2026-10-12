package com.arcadia.aiscompose.View

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*       // Column, Row, Spacer, padding, weight, etc.
import androidx.compose.material3.*             // Text, Button, Material3 components
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arcadia.aiscompose.Model.DynamicPivot
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.safeDrawingPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicPivotTableScreen(data: List<DynamicPivot>) {
    val options = listOf("normal", "kilo", "mio")
    var selectedUnit by remember { mutableStateOf("normal") }
    var expanded by remember { mutableStateOf(false) }

    val monthOrder = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    val allMonths = remember(data) {
        data.flatMap { it.values.keys }
            .distinct()
            .sortedWith(compareBy { monthOrder.indexOf(it) })
    }

    // Hitung total per bulan
    val totals = remember(data) {
        allMonths.associateWith { month ->
            data.sumOf { it.values[month]?.toDouble() ?: 0.0 }.toFloat()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Data size: ${data.size}", style = MaterialTheme.typography.labelSmall)

            // Dropdown pilihan unit
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
//                    TextField(
//                        value = selectedUnit,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("View In") },
//                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                        modifier = Modifier.menuAnchor()
//                    )
                    OutlinedTextField(
                        value = selectedUnit,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text("View In") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedUnit = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Kategori",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    style = MaterialTheme.typography.labelLarge
                )
                allMonths.forEach { month ->
                    Text(
                        month.uppercase(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Divider()

            LazyColumn (
                modifier = Modifier.weight(1f)
            ){
                items(data.size) { index ->
                    val row = data[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            row.coaName,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        allMonths.forEach { month ->
                            val value = row.values[month] ?: 0f
                            val displayValue = when (selectedUnit) {
                                "kilo" -> value / 1_000f
                                "mio" -> value / 1_000_000f
                                else -> value
                            }
                            Text(
                                text = "%,.2f".format(displayValue),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }
                }

                // Tambah row TOTAL di bawah
                item {
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "TOTAL",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace
                            )
                        )
                        allMonths.forEach { month ->
                            val rawTotal = totals[month] ?: 0f
                            val displayTotal = when (selectedUnit) {
                                "kilo" -> rawTotal / 1_000f
                                "mio" -> rawTotal / 1_000_000f
                                else -> rawTotal
                            }
                            Text(
                                text = "%,.2f".format(displayTotal),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
