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
import com.arcadia.aiscompose.Model.CreditCardItem
import androidx.compose.foundation.isSystemInDarkTheme
import com.arcadia.aiscompose.Model.MaintenanceItem

@Composable
fun MaintenanceChartView(data: List<MaintenanceItem>
) {
    val isDarkTheme = isSystemInDarkTheme()

    AndroidView(factory = { context ->
        PieChart(context).apply {

            val item = data.sortedBy { it.maintenance_name }.firstOrNull() ?: return@AndroidView PieChart(context)

            val used = (item.timeline - item.remaining).toFloat().coerceAtLeast(0f)
            val remaining = item.remaining.toFloat()

            //val entries2 = listOf(PieEntry(item.remaining.toFloat(), item.name))

            val entries = listOf(
                PieEntry(used, "Used ${item.legend_uom}"),
                PieEntry(remaining, "Remaining ${item.legend_uom}")
            )

            val dataSet = PieDataSet(entries, item.maintenance_name+item.asset_name)
            dataSet.colors = listOf(
                AndroidColor.rgb(244, 67, 54),   // Merah
                AndroidColor.rgb(33, 150, 243),  // Biru
            )
            dataSet.valueTextSize = 14f
            dataSet.sliceSpace = 3f

            val data = PieData(dataSet)

            this.data = data
            this.description.isEnabled = false
            this.centerText = "Total"


            val legendColor = if (isDarkTheme) AndroidColor.WHITE else AndroidColor.BLACK
            this.legend.textColor = legendColor
            this.setEntryLabelColor(legendColor)
            this.setCenterTextColor(legendColor) // opsional

            //this.setEntryLabelColor(AndroidColor.BLACK)
            this.animateY(1000)
            this.invalidate() // refresh
        }
    },
        modifier = Modifier
            .height(600.dp) // ✅ Ukuran diperlukan agar AndroidView bisa tampil
            .fillMaxWidth()
    )
}

