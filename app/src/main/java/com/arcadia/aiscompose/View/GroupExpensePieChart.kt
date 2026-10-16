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
    val legendColor = if (isDarkTheme) {
        AndroidColor.WHITE
    } else {
        AndroidColor.BLACK
    }

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

                // Palet warna tambahan
                val chartColors = listOf(
                    AndroidColor.rgb(244, 67, 54),
                    AndroidColor.rgb(33, 150, 243),
                    AndroidColor.rgb(76, 175, 80),
                    AndroidColor.rgb(255, 193, 7),
                    AndroidColor.rgb(156, 39, 176),
                    AndroidColor.rgb(0, 188, 212),
                    AndroidColor.rgb(255, 87, 34),
                    AndroidColor.rgb(121, 85, 72),
                    AndroidColor.rgb(63, 81, 181),
                    AndroidColor.rgb(205, 220, 57),
                    AndroidColor.rgb(233, 30, 99),
                    AndroidColor.rgb(0, 150, 136),
                    AndroidColor.rgb(103, 58, 183),
                    AndroidColor.rgb(158, 158, 158),
                    AndroidColor.rgb(255, 152, 0)
                )

//                val dataSet = PieDataSet(entries, "")
//                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
//                dataSet.valueTextSize = 14f
//                dataSet.sliceSpace = 3f



                val dataSet = PieDataSet(entries, "").apply {
                    colors = List(entries.size) { index ->
                        chartColors[index % chartColors.size]
                    }
                    valueTextSize = 12f
                    sliceSpace = 3f
                }

                val pieData = PieData(dataSet)

                this.data = pieData
                this.description.isEnabled = false
                //this.setUsePercentValues(true)
                this.centerText = "Bulan $latestMonth"
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
//        update = { chart ->
//            val textColor = if (isDarkTheme) {
//                AndroidColor.WHITE
//            } else {
//                AndroidColor.BLACK
//            }
//
//            // Warna tulisan di tengah grafik
//            chart.setCenterTextColor(textColor)
//
//            // Warna label kategori dan legend
//            chart.setEntryLabelColor(textColor)
//            chart.legend.textColor = textColor
//
//            chart.invalidate()
//        },
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    )
}
