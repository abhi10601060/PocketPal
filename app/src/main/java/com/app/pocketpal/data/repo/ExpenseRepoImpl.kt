package com.app.pocketpal.data.repo

import com.app.pocketpal.data.room.dao.ExpenseDao
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.repo.ExpenseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepoImpl @Inject constructor(
    val expenseDao: ExpenseDao
) : ExpenseRepo {

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.upsertExpense(expense)
    }

    override suspend fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getExpenses()
    }
}