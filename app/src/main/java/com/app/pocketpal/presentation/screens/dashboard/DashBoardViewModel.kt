package com.app.pocketpal.presentation.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.ui.piechart.models.PieChartData
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.model.ExpensePieChartData
import com.app.pocketpal.domain.use_case.getPieChartData.GetPieChartDataUseCase
import com.app.pocketpal.domain.use_case.get_all_expense.GetAllExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashBoardViewModel @Inject constructor(val getAllExpenseUseCase: GetAllExpenseUseCase, val getPieChartDataUseCase: GetPieChartDataUseCase) : ViewModel() {
    private val _listOfExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val listOfExpenses : StateFlow<List<Expense>> = _listOfExpenses

    private val _pieChartData = MutableStateFlow<List<ExpensePieChartData>>(emptyList())
    val pieChartData : StateFlow<List<ExpensePieChartData>> = _pieChartData

    init {
        viewModelScope.launch {
            getAllExpenseUseCase().collect { it ->
                _listOfExpenses.emit(it)
            }
        }
        getPieChartData()
    }

    fun getPieChartData(){
        viewModelScope.launch {
            _listOfExpenses.collect {
                val ans = getPieChartDataUseCase.getPieChartData(it)
                _pieChartData.emit(ans)
            }
        }
    }


}