package com.app.pocketpal.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.pocketpal.data.room.dao.ExpenseDao
import com.app.pocketpal.data.room.model.Expense

@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseDB : RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
}