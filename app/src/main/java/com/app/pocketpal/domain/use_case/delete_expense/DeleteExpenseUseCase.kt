package com.app.pocketpal.domain.use_case.delete_expense

import com.app.pocketpal.domain.repo.ExpenseRepo
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(val expenseRepo: ExpenseRepo) {

    suspend operator fun invoke(id: String) {
        expenseRepo.deleteExpenseById(id)
    }

}