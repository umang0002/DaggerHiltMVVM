package com.example.daggerhiltmvvm.repository

import com.example.daggerhiltmvvm.database.BlogDao
import com.example.daggerhiltmvvm.database.CacheMapper
import com.example.daggerhiltmvvm.model.Blog
import com.example.daggerhiltmvvm.network.BlogApi
import com.example.daggerhiltmvvm.network.BlogMapper
import com.example.daggerhiltmvvm.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository constructor(
    private val blogDao: BlogDao,
    private val blogApi: BlogApi,
    private val cacheMapper: CacheMapper,
    private val blogMapper: BlogMapper
) {

    suspend fun getBlog() : Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1000)
        try {
            val networkBlogs = blogApi.get()
            val blogs = blogMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cacheBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheBlogs)))
        }catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}