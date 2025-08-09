package com.app.pocketpal.domain.model

import androidx.compose.ui.graphics.Color

data class ExpensePieChartData(
    val label : String,
    val percentage : Float,
    val color : Color,
)


fun Label.toExpensePieChartData() : ExpensePieChartData {
    return ExpensePieChartData(label = this.name, percentage = 0f, color = this.color)
}