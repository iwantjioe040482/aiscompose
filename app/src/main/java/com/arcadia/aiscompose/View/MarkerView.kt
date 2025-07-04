package com.arcadia.aiscompose.View

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.arcadia.aiscompose.R

class MarkerView(context: Context, private val labels: List<String>) : MarkerView(context, R.layout.marker_view) {

    private val markerText: TextView = findViewById(R.id.marker_text)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            val index = it.x.toInt()
            val label = labels.getOrNull(index) ?: "-"
            markerText.text = "Value: ${it.y.toFloat()}"
        }
        super.refreshContent(e, highlight)
    }


}
