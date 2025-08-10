package com.app.pocketpal.domain.model

import androidx.compose.ui.graphics.Color
import co.yml.charts.ui.piechart.models.PieChartData

data class ExpensePieChartData(
    val label : String,
    val percentage : Float,
    val color : Color,
    val total : Int
)


fun ExpensePieChartData.toPieChartSlice() : PieChartData.Slice{
    return PieChartData.Slice(this.label,this.percentage,this.color)
}