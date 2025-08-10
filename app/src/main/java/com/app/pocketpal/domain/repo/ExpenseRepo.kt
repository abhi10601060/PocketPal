package com.app.pocketpal.domain.repo

import com.app.pocketpal.data.room.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepo {
    suspend fun insertExpense(expense: Expense)

    suspend fun getAllExpenses() : Flow<List<Expense>>

    suspend fun deleteExpenseById(id : String)
}