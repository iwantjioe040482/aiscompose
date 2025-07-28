package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
import com.github.mikephil.charting.formatter.PercentFormatter
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
    fun ExpensePriorityChartView(data: List<PriorityItem>
    ) {
        val isDarkTheme = isSystemInDarkTheme()
        val centerTextState = remember { mutableStateOf("Total") }

        AndroidView(factory = { context ->
            PieChart(context).apply {

                val item = data.sortedBy { it.bulan }.firstOrNull() ?: return@AndroidView PieChart(context)

                val used = (item.kebutuhan).toFloat().coerceAtLeast(0f)
                val remaining = item.keinginan.toFloat()
                val total = used + remaining // ✅ Tambahkan baris ini
                if (total <= 0f) return@AndroidView PieChart(context)

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

                // ✅ Tampilkan persentase
                this.setUsePercentValues(true)

//                this.isHighlightPerTapEnabled = true

                this.data = data
                this.data.setValueFormatter(PercentFormatter(this))
                // Jadi seperti ini:
                this.centerText = "Total"
                val centerColor = if (isDarkTheme) AndroidColor.BLACK else AndroidColor.WHITE
                this.setCenterTextColor(centerColor)
                this.description.isEnabled = false


                val legendColor = if (isDarkTheme) AndroidColor.WHITE else AndroidColor.BLACK
                this.legend.textColor = legendColor
                this.setEntryLabelColor(legendColor)
                this.setCenterTextColor(legendColor) // opsional

                // ✅ Listener klik pie slice
                this.setOnChartValueSelectedListener(object : com.github.mikephil.charting.listener.OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: com.github.mikephil.charting.highlight.Highlight?) {
                        e?.let {
                            val pieEntry = e as PieEntry
                            val label = pieEntry.label
                            val value = pieEntry.value
                            val percent = value / (used + remaining) * 100
                            this@apply.centerText = "$label: ${String.format("%.1f", percent)}%\nRp ${String.format("%,.0f", value)}"
                            // Jadi seperti ini:
                            this@apply.setCenterTextColor(centerColor)
                            Log.d("ChartView","Keklik")
//
//                            val label = pieEntry.label
//                            val value = pieEntry.value
//                            val percent = (value / total) * 100
//                            this@apply.centerText = "$label: ${String.format("%.1f", percent)}%"
                            this@apply.invalidate()
                        }
                    }

                    override fun onNothingSelected() {
                        this@apply.centerText = "Total"
                        //this@apply.setCenterTextColor(legendColor)
                        //Log.d("ChartView","warna$legendColorop")
                        //this@apply.invalidate()
                    }
                })

                //this.setEntryLabelColor(AndroidColor.BLACK)
                this.animateY(1000)
                this.invalidate() // refresh
            }
        },
            modifier = Modifier
                .height(600.dp) // ✅ Ukuran diperlukan agar AndroidView bisa tampil
                .fillMaxWidth()
        )
//
//        // Teks di tengah chart dengan background
//        Box(
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .background(
//                    color = if (isDarkTheme) Color.Black else Color.White,
//                    shape = RoundedCornerShape(12.dp)
//                )
//                .padding(8.dp)
//        ) {
//            Text(
//                text = centerTextState.value,
//                color = if (isDarkTheme) Color.White else Color.Black,
//                textAlign = TextAlign.Center
//            )
//        }
        }


