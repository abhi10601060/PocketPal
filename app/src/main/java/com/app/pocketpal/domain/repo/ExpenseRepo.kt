package com.app.pocketpal.domain.repo

import com.app.pocketpal.data.room.model.Expense

interface ExpenseRepo {
    suspend fun insertExpense(expense: Expense)

}