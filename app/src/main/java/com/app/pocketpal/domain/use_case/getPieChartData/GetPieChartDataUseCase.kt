package com.app.pocketpal.domain.use_case.getPieChartData

import co.yml.charts.ui.piechart.models.PieChartData
import com.app.pocketpal.constant.LABEL_LIST
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.model.ExpensePieChartData
import javax.inject.Inject

class GetPieChartDataUseCase @Inject constructor() {

    fun getPieChartData(list : List<Expense>) : List<ExpensePieChartData>{
        val labelMap = mutableMapOf<String, Int>()
        var total = 0;
        list.forEach {
            total += it.amount
            labelMap.put(it.label, labelMap.getOrDefault(it.label, 0) + it.amount)
        }
        val data = mutableListOf<ExpensePieChartData>()
        LABEL_LIST.forEach {
            val amount = labelMap.getOrDefault(it.name, 0)
            val percents = (labelMap.getOrDefault(it.name, 0).toFloat() / total) * 100.00f
            data.add(ExpensePieChartData(label = it.name, percentage = percents, color = it.color, total = amount))
        }
        return data
    }

}