package com.app.pocketpal.domain.use_case.get_all_expense

import com.app.pocketpal.domain.repo.ExpenseRepo
import javax.inject.Inject

class GetAllExpenseUseCase @Inject constructor(val repo : ExpenseRepo) {

    suspend operator fun invoke() = repo.getAllExpenses()
}