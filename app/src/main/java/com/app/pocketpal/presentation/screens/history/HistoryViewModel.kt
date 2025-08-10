package com.app.pocketpal.presentation.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.use_case.filter_by_days.FilterByDaysUseCase
import com.app.pocketpal.domain.use_case.get_all_expense.GetAllExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@HiltViewModel
class HistoryViewModel @Inject constructor(val getAllExpenseUseCase: GetAllExpenseUseCase, val filterByDaysUseCase: FilterByDaysUseCase) : ViewModel() {

    private val _listOfExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val listOfExpenses : StateFlow<List<Expense>> = _listOfExpenses

    private val _filteredListOfExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val filteredListOfExpenses : StateFlow<List<Expense>> = _filteredListOfExpenses

    var startDate by mutableStateOf(LocalDate.now().minusDays(7))
    var endDate by mutableStateOf(LocalDate.now())

    var todayAmount = 0


    init {
        viewModelScope.launch {
            getAllExpenseUseCase().collect { it ->
                _listOfExpenses.emit(it)
            }
        }
        getTodayAmount()
        filterExpenses()
    }

    fun getTodayAmount(){
        viewModelScope.launch {
            listOfExpenses.collect{
                filterByDaysUseCase.invoke(it, LocalDate.now(), LocalDate.now()).collect {
                    todayAmount = it.sumOf { it.amount }
                }
            }
        }
    }

    fun filterExpenses(){
        viewModelScope.launch {
            _listOfExpenses.collect{
                filterByDaysUseCase.invoke(it, startDate, endDate).collect {
                    _filteredListOfExpenses.emit(it)
                }
            }
        }
    }

    fun filterWhenDateChanges(){
        viewModelScope.launch {
            filterByDaysUseCase.invoke(listOfExpenses.value, startDate, endDate).collect {
                _filteredListOfExpenses.emit(it)
            }
        }
    }
}