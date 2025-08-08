package com.app.pocketpal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getDiTestData() : DiTest{
        return DiTest("Hello from hilt...")
    }
}

class DiTest(val name : String){}