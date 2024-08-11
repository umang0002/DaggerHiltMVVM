package com.example.daggerhiltmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.daggerhiltmvvm.database.BlogDao
import com.example.daggerhiltmvvm.database.BlogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context) : BlogDatabase {

        return Room.databaseBuilder(
            context, BlogDatabase::class.java,
            BlogDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDao(blogDatabase: BlogDatabase) : BlogDao{
        return blogDatabase.blogDao()
    }
}