package com.app.pocketpal.presentation.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.use_case.get_all_expense.GetAllExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HistoryViewModel @Inject constructor(val getAllExpenseUseCase: GetAllExpenseUseCase) : ViewModel() {

    private val _listOfExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val listOfExpenses : StateFlow<List<Expense>> = _listOfExpenses

    init {
        viewModelScope.launch {
            getAllExpenseUseCase().collect { it ->
                _listOfExpenses.emit(it)
            }
        }
    }
}