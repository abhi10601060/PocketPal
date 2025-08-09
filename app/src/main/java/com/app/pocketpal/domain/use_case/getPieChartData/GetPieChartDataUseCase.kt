package com.app.pocketpal.domain.use_case.getPieChartData

import co.yml.charts.ui.piechart.models.PieChartData
import com.app.pocketpal.constant.LABEL_LIST
import com.app.pocketpal.data.room.model.Expense
import javax.inject.Inject

class GetPieChartDataUseCase @Inject constructor() {

    fun getPieChartData(list : List<Expense>) : List<PieChartData.Slice>{
        val labelMap = mutableMapOf<String, Int>()
        var total = 0;
        list.forEach {
            total += it.amount
            labelMap.put(it.label, labelMap.getOrDefault(it.label, 0) + it.amount)
        }
        val data = mutableListOf<PieChartData.Slice>()
        LABEL_LIST.forEach {
            val percents = (labelMap.getOrDefault(it.name, 0).toFloat() / total) * 100.00f
            data.add(PieChartData.Slice(it.name, percents, it.color))
        }
        return data
    }

}