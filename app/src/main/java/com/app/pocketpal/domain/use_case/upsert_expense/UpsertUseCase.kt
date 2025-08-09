package com.app.pocketpal.domain.use_case.upsert_expense

import com.app.pocketpal.data.repo.ExpenseRepoImpl
import com.app.pocketpal.data.room.model.Expense
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpsertUseCase @Inject constructor(val repo : ExpenseRepoImpl){

    operator fun invoke(expense: Expense)  = flow {
        try {
            repo.insertExpense(expense)
            emit(true)
        }
        catch (e : Exception){
            emit(false)
        }

    }
}