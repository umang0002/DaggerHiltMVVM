@file:Suppress("DEPRECATION")

package com.example.daggerhiltmvvm.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhiltmvvm.model.Blog
import com.example.daggerhiltmvvm.repository.MainRepository
import com.example.daggerhiltmvvm.utils.DataState
import dagger.assisted.Assisted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState : LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainSateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainSateEvent) {
                is MainStateEvent.GetBlogEvent -> {
                    mainRepository.getBlog()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    //
                }
            }
        }
    }
}

sealed class MainStateEvent {
    object GetBlogEvent : MainStateEvent()
    object None : MainStateEvent()
}