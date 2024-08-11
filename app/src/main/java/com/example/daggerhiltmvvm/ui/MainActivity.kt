package com.example.daggerhiltmvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.example.daggerhiltmvvm.databinding.ActivityMainBinding
import com.example.daggerhiltmvvm.model.Blog
import com.example.daggerhiltmvvm.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BlogAdapter.BlogItemListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()
    private lateinit var adapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvent)


        binding.swipeRefreshLayout.setOnClickListener {
            viewModel.setStateEvent(MainStateEvent.GetBlogEvent)
        }


    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayLoading(false)
                    populateRecyclerView(dataState.data)
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isLoading : Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun populateRecyclerView(blogs : List<Blog>) {
        if (blogs.isNotEmpty()) adapter.setItems(ArrayList(blogs))
    }

    private fun setupRecyclerView() {
        adapter = BlogAdapter(this)
        binding.blogRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.blogRecyclerview.adapter = adapter
    }


    override fun onClickedBlog(blogTitle: CharSequence) {
        Toast.makeText(this, blogTitle, Toast.LENGTH_LONG).show()
    }
}