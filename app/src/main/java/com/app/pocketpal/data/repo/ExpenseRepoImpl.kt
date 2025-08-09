package com.app.pocketpal.data.repo

import com.app.pocketpal.data.room.dao.ExpenseDao
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.repo.ExpenseRepo
import javax.inject.Inject

class ExpenseRepoImpl @Inject constructor(
    val expenseDao: ExpenseDao
) : ExpenseRepo {

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.upsertExpense(expense)
    }

}