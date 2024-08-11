package com.example.daggerhiltmvvm.di

import android.app.Application
import com.example.daggerhiltmvvm.database.BlogDao
import com.example.daggerhiltmvvm.database.CacheMapper
import com.example.daggerhiltmvvm.network.BlogApi
import com.example.daggerhiltmvvm.network.BlogMapper
import com.example.daggerhiltmvvm.repository.MainRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager

@InstallIn(ApplicationComponentManager::class)
@Module
object RepositoryModule {

    fun provideMainRepository(
        blogDao: BlogDao,
        blogApi: BlogApi,
        cacheMapper: CacheMapper,
        blogMapper: BlogMapper
    ) : MainRepository {
        return MainRepository(blogDao, blogApi, cacheMapper, blogMapper)
    }
}