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

@Composable
fun ElectricityLineChartView(data: List<ElectricityView>) {
    val entries = data
        .sortedBy { it.date }
        .mapIndexed { index, item -> Entry(index.toFloat(), item.difference.toFloat()) }

    val labels = data
        .sortedBy { it.date }
        .map { it.date.substring(5) } // Show only MM-DD

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // ✅ Ubah ukuran sesuai kebutuhan
            .padding(8.dp),
        factory = { context: Context ->
            LineChart(context).apply {
                val dataSet = LineDataSet(entries, "Water Usage")
                dataSet.color = Color.BLUE
                dataSet.valueTextColor = Color.BLACK
                dataSet.setCircleColor(Color.BLUE)
                dataSet.setDrawValues(true)

                this.data = LineData(dataSet)

                val marker = MarkerView(context, labels)
                marker.chartView = this
                marker.offset = MPPointF(-50f, -50f)
                this.marker = marker

                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)

                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false

                invalidate()
            }
        })
}
