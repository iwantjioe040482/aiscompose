package com.arcadia.aiscompose.View

import android.util.Log
import android.graphics.Color as AndroidColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.components.XAxis
import com.arcadia.aiscompose.Model.Expense
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

@Composable
fun ExpenseChartView(expenses: List<Expense>) {
    Column {
        Spacer(modifier = Modifier.height(48.dp))
        //var selectedCoa by remember { mutableStateOf<String?>(null) }
        val coaList = expenses.map { it.coa_name }.distinct()
        var selectedCoa by remember { mutableStateOf(coaList.firstOrNull()) }

        val filteredExpenses = selectedCoa?.let { selected ->
            expenses.filter { it.coa_name == selected }
        } ?: expenses

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Dropdown Filter
            var expanded by remember { mutableStateOf(false) }

            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedCoa ?: "Pilih Akun COA")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                DropdownMenuItem(
//                    text = { Text("Semua Akun") },
//                    onClick = {
//                        selectedCoa = null
//                        expanded = false
//                    }
//                )
                coaList.forEach { coa ->
                    DropdownMenuItem(
                        text = { Text(coa) },
                        onClick = {
                            selectedCoa = coa
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { context ->
                    LineChart(context)
                },
                update = { chart ->
                    val amounts = filteredExpenses.map { it.total }
                    val labels = filteredExpenses
                        .sortedBy { it.gl_month }
                        .map { it.gl_month.substring(5) }

                    val entries = amounts.mapIndexed { index, value ->
                        Entry(index.toFloat(), value.toFloat())
                    }

                    val ma100 = calculateMA(amounts, 2)
                    val ma100Entries = ma100.mapIndexedNotNull { index, value ->
                        value?.let { Entry(index.toFloat(), it.toFloat()) }
                    }

                    val ma200 = calculateMA(amounts, 3)
                    val ma200Entries = ma200.mapIndexedNotNull { index, value ->
                        value?.let { Entry(index.toFloat(), it.toFloat()) }
                    }

                    val setPengeluaran = LineDataSet(entries, "Pengeluaran").apply {
                        color = AndroidColor.BLACK
                        valueTextSize = 8f
                        setDrawCircles(false)
                        setDrawValues(true)
                        lineWidth = 2f
                    }

                    val setMA100 = LineDataSet(ma100Entries, "MA100").apply {
                        color = AndroidColor.BLUE
                        setDrawCircles(false)
                        lineWidth = 2f
                    }

                    val setMA200 = LineDataSet(ma200Entries, "MA200").apply {
                        color = AndroidColor.RED
                        setDrawCircles(false)
                        lineWidth = 2f
                    }

                    val dataSets = mutableListOf<ILineDataSet>(setPengeluaran)
                    if (ma100Entries.isNotEmpty()) {
                        dataSets.add(setMA100)
                    }
                    if (ma200Entries.isNotEmpty()) {
                        dataSets.add(setMA200)
                    }

                    chart.data = LineData(dataSets)

                    val markerView = ExpenseMarkerView(chart.context)
                    markerView.chartView = chart
                    chart.marker = markerView

                    chart.description.isEnabled = false
                    chart.legend.isEnabled = true

                    chart.xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        valueFormatter = IndexAxisValueFormatter(labels)
                    }
                    chart.legend.isEnabled = dataSets.size > 1

                    chart.axisRight.isEnabled = false
                    chart.invalidate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }

}

fun calculateMA(values: List<Double>, period: Int): List<Double?> {
    return values.mapIndexed { index, _ ->
        if (index >= period - 1) {
            val window = values.subList(index - period + 1, index + 1)
            window.average()
        } else null
    }
}

