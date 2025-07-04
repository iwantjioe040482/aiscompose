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

@Composable
fun TaxChartView(data: List<TaxItem>
) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            // Data
            //val entries = data
            //    .sortedBy { it.name }
                //.mapIndexed { index, item -> PieEntry(item.remaining.toFloat(), "remainning days") }
            //Spacer(modifier = Modifier.height(32.dp))

            //val entries = listOf(
            //    PieEntry(40f, "Makan"),
            //    PieEntry(30f, "Transportasi"),
            //    PieEntry(20f, "Listrik"),
             //   PieEntry(10f, "Lainnya")
            //)

            val item = data.sortedBy { it.name }.firstOrNull() ?: return@AndroidView PieChart(context)

            val used = (item.timeline - item.remaining).toFloat().coerceAtLeast(0f)
            val remaining = item.remaining.toFloat()

            //val entries2 = listOf(PieEntry(item.remaining.toFloat(), item.name))

            val entries = listOf(
                PieEntry(used, "Used Days"),
                PieEntry(remaining, "Remaining Days")
            )

            val dataSet = PieDataSet(entries, item.name)
            dataSet.colors = listOf(
                AndroidColor.rgb(244, 67, 54),   // Merah
                AndroidColor.rgb(33, 150, 243),  // Biru
                //AndroidColor.rgb(76, 175, 80),   // Hijau
                //AndroidColor.rgb(255, 193, 7)    // Kuning
            )
            dataSet.valueTextSize = 14f
            dataSet.sliceSpace = 3f

            val data = PieData(dataSet)

            this.data = data
            this.description.isEnabled = false
            this.centerText = "Total"
            this.setEntryLabelColor(AndroidColor.BLACK)
            this.animateY(1000)
            this.invalidate() // refresh
        }
    },
            modifier = Modifier
            .height(600.dp) // ✅ Ukuran diperlukan agar AndroidView bisa tampil
        .fillMaxWidth()
    )
    // Data yang ingin ditampilkan
    //val entries = listOf(
    //    PieChartEntry(40f, "Listrik"),
    //    PieChartEntry(30f, "Air"),
    //    PieChartEntry(20f, "Internet"),
    //    PieChartEntry(10f, "Lainnya")
    //)

    //val entryModel: PieChartEntryModel = PieChartEntryModelImpl(entries)

    // Warna untuk setiap slice
   // val colors = listOf(
    //    Color(0xFF4CAF50),
    //    Color(0xFF2196F3),
    //    Color(0xFFFFC107),
    //    Color(0xFFF44336)
    //)

    //PieChart(
    //    chart = pieChart(
    //        sliceThickness = 40.dp, // membuatnya jadi donut
     //   ),
    //    model = entryModel,
    //    modifier = modifier,
     //   colors = colors
    //)
}


