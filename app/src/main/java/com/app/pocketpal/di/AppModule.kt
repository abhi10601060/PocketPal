package com.app.pocketpal.di

import android.content.Context
import androidx.room.Room
import com.app.pocketpal.data.repo.ExpenseRepoImpl
import com.app.pocketpal.data.room.dao.ExpenseDao

import com.app.pocketpal.data.room.db.ExpenseDB
import com.app.pocketpal.domain.repo.ExpenseRepo
import com.app.pocketpal.domain.use_case.save_images.SaveImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getDiTestData() : DiTest{
        return DiTest("Hello from hilt...")
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): ExpenseDB {
        return Room.databaseBuilder(context, ExpenseDB::class.java, "expense_database").build()
    }

    @Provides
    fun provideUserDao(database: ExpenseDB): ExpenseDao {
        return database.expenseDao()
    }

    @Provides
    fun provideExpenseRepo(expenseDao: ExpenseDao) : ExpenseRepo{
        return ExpenseRepoImpl(expenseDao)
    }

    @Provides
    fun providesSaveImagesUseCase(@ApplicationContext context: Context) : SaveImagesUseCase{
        return SaveImagesUseCase(context)
    }
}

class DiTest(val name : String){}