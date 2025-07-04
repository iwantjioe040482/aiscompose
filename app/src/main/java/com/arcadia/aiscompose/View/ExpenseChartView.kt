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

@Composable
fun ExpenseChartView(expenses: List<Expense>) {
    Column {
        Spacer(modifier = Modifier.height(48.dp))
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    val amounts = expenses.map { it.total }
//                    Log.d("ExpenseChartView", "Amounts: $amounts")
                    val labels = expenses
                        .sortedBy { it.gl_month }
                        .map { it.gl_month.substring(5) } // Show only MM-DD

                    // Entry data pengeluaran
                    val entries = amounts.mapIndexed { index, value ->
                        Entry(index.toFloat(), value.toFloat())
                    }

                    // MA100 dan MA200
                    val ma100 = calculateMA(amounts, 2)
                    val ma100Entries = ma100.mapIndexedNotNull { index, value ->
                        value?.let { Entry(index.toFloat(), it.toFloat()) }
                    }

                    val ma200 = calculateMA(amounts, 3)
                    val ma200Entries = ma200.mapIndexedNotNull { index, value ->
                        value?.let { Entry(index.toFloat(), it.toFloat()) }
                    }
                    // DataSet
                    val setPengeluaran = LineDataSet(entries, "Pengeluaran").apply {
                        color = AndroidColor.BLACK
                        valueTextSize = 8f
                        setDrawCircles(false)
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

                    val lineData = LineData(setPengeluaran, setMA100, setMA200)
                    this.data = lineData

                    this.description.isEnabled = false
                    this.legend.isEnabled = true

                    // Atur X Axis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)

                    xAxis.valueFormatter = IndexAxisValueFormatter(labels)
//            xAxis.granularity = 1f
//            xAxis.labelRotationAngle = -45f // optional untuk miringkan label

                    axisRight.isEnabled = false
                    invalidate()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
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



