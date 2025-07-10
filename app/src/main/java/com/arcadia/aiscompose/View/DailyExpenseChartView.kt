package com.arcadia.aiscompose.View

import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.arcadia.aiscompose.Model.ElectricityView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import com.github.mikephil.charting.utils.MPPointF
import androidx.compose.foundation.isSystemInDarkTheme
import com.arcadia.aiscompose.Model.DailyReport

@Composable
fun DailyExpenseChartView(data: List<DailyReport>) {
    val isDarkTheme = isSystemInDarkTheme()
    val entries = data
        .sortedBy { it.gl_date }
        .mapIndexed { index, item -> Entry(index.toFloat(), item.total.toFloat()) }

    val labels = data
        .sortedBy { it.gl_date }
        .map { it.gl_date.substring(5) } // Show only MM-DD



    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // ✅ Ubah ukuran sesuai kebutuhan
            .padding(top = 64.dp), // ✅ geser turun 16dp dari atas
        //            .padding(8.dp),
        factory = { context: Context ->
            LineChart(context).apply {
                val numberFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return String.format("%,.2f", value) // Format: 1,234.56
                    }
                }
                val dataSet = LineDataSet(entries, "Electricity Usage")
                dataSet.color = Color.BLUE
                val legendColor = if (isDarkTheme) Color.WHITE else Color.BLACK

                axisLeft.textColor = legendColor
                axisLeft.setDrawGridLines(true) // Opsional

                val textSize = 12f // ← Adjust this as needed (in sp)

                dataSet.valueTextSize = textSize
                //axisLeft.textSize = textSize
                xAxis.textSize = textSize

                dataSet.valueTextColor = legendColor
                dataSet.setCircleColor(Color.BLUE)
                dataSet.setDrawValues(true)
                dataSet.valueFormatter = numberFormatter

                this.data = LineData(dataSet)

                val marker = MarkerView(context, labels)
                marker.chartView = this
                marker.offset = MPPointF(-50f, -50f)
                this.marker = marker

                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.textColor=legendColor
                xAxis.setDrawGridLines(false)

                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                legend.textColor=legendColor
                invalidate()
            }
        })
}
