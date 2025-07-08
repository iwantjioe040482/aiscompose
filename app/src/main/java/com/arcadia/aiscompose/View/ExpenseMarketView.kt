package com.arcadia.aiscompose.View

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.arcadia.aiscompose.R // ganti jika perlu
import android.view.LayoutInflater
import android.view.View
import android.graphics.Canvas
import java.text.DecimalFormat

class ExpenseMarkerView(context: Context) : MarkerView(context, R.layout.expense_marker_view) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val formatter = DecimalFormat("#,##0.00")

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            tvContent.text = "Rp ${formatter.format(it.y)}"
        }
        super.refreshContent(e, highlight)
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        // Adjust marker position
        val width = width
        val height = height

        var x = posX - width / 2
        var y = posY - height

        if (x < 0) x = 0f
        if (y < 0) y = 0f

        canvas.translate(x, y)
        draw(canvas)
        canvas.translate(-x, -y)
    }
}