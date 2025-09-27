package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
//import com.patrykandpatrick.vico.compose.chart.pie.PieChart
//import com.patrykandpatrick.vico.core.entry.pie.PieChartEntry
//import com.patrykandpatrick.vico.core.entry.pie.PieChartEntryModel
//import com.patrykandpatrick.vico.core.entry.pie.PieChartEntryModelImpl
//import com.patrykandpatrick.vico.compose.chart.pie.pieChart
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.arcadia.aiscompose.Model.TaxItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.fillMaxWidth
import com.arcadia.aiscompose.Model.Expense
import androidx.compose.foundation.isSystemInDarkTheme
import com.arcadia.aiscompose.View.GroupExpensePieChart
import com.github.mikephil.charting.utils.ColorTemplate


@Composable
fun GroupExpensePieChart(data: List<Expense>) {
    val isDarkTheme = isSystemInDarkTheme()

    // Filter data untuk bulan terbaru
    val latestMonth = data.maxByOrNull { it.gl_month }?.gl_month
    val dataForMonth = data.filter { it.gl_month == latestMonth }.sortedByDescending { it.total }

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                if (dataForMonth.isEmpty()) return@AndroidView PieChart(context)

                val entries = dataForMonth.map {
                    PieEntry(it.total.toFloat(), it.coa_name)
                }

                val dataSet = PieDataSet(entries, "")
                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                dataSet.valueTextSize = 14f
                dataSet.sliceSpace = 3f

                val pieData = PieData(dataSet)

                this.data = pieData
                this.description.isEnabled = false
                //this.setUsePercentValues(true)
                this.centerText = "Expense $latestMonth"
                this.setCenterTextSize(18f)

                val legendColor = if (isDarkTheme) AndroidColor.WHITE else AndroidColor.BLACK
                this.legend.textColor = legendColor
                this.setEntryLabelColor(legendColor)
                this.setCenterTextColor(legendColor)

                // ✅ Konfigurasi legend agar multi-wrap
                this.legend.apply {
                    isWordWrapEnabled = true
                    maxSizePercent = 1f
                    yEntrySpace = 5f
                    form = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE
                    verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
                    orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
                    setDrawInside(false)
                    textColor = legendColor
                    textSize = 12f
                }

                this.animateY(1000)
                this.invalidate()
            }
        },
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    )
}
