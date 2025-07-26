package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.fillMaxWidth
import com.arcadia.aiscompose.Model.PriorityItem
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
    fun ExpensePriorityChartView(data: List<PriorityItem>
    ) {
        val isDarkTheme = isSystemInDarkTheme()

        AndroidView(factory = { context ->
            PieChart(context).apply {

                val item = data.sortedBy { it.bulan }.firstOrNull() ?: return@AndroidView PieChart(context)

                val used = (item.kebutuhan).toFloat().coerceAtLeast(0f)
                val remaining = item.keinginan.toFloat()

                val entries = listOf(
                    PieEntry(used, "Kebutuhan"),
                    PieEntry(remaining, "Keinginan")
                )

                val dataSet = PieDataSet(entries, item.bulan)
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


