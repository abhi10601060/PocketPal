package com.app.pocketpal.domain.use_case.filter_by_days

import android.icu.util.Calendar
import com.app.pocketpal.data.room.model.Expense
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class FilterByDaysUseCase @Inject constructor() {

    operator fun invoke(list : List<Expense>, startDate : LocalDate, endDate : LocalDate)  = flow {
//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.DAY_OF_MONTH, -(days - 1))
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
//        calendar.set(Calendar.MINUTE, 0)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//        val startTime = calendar.timeInMillis

        val startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endDateMillis = endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val filteredList = list.filter { expense ->
            expense.createdAt >= startMillis && expense.createdAt <= endDateMillis
        }
        emit(filteredList)
    }
}