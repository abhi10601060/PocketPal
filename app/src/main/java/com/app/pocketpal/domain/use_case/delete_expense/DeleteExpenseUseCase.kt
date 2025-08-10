package com.app.pocketpal.domain.use_case.delete_expense

import android.content.Context
import com.app.pocketpal.domain.repo.ExpenseRepo
import java.io.File
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(val expenseRepo: ExpenseRepo, val context : Context) {

    suspend operator fun invoke(id: String) {
        expenseRepo.deleteExpenseById(id)
        deleteImageFolder(id)
    }

    private fun deleteImageFolder(id: String){
        val picturesDir = context.getExternalFilesDir(null)
        val expenseFolder = File(picturesDir, id)
        try {
            if (expenseFolder.exists()) {
                expenseFolder.deleteRecursively()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}