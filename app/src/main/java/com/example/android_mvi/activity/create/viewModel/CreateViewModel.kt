package com.example.android_mvi.activity.create.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mvi.activity.create.intenstate.CreateIntent
import com.example.android_mvi.model.Post
import com.example.android_mvi.repository.CreateRepository
import com.example.android_mvi.activity.create.intenstate.CreateState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CreateViewModel(private val repository: CreateRepository) : ViewModel() {

    val createIntent = Channel<CreateIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<CreateState>(CreateState.Init)
    val state: StateFlow<CreateState> get() = _state

    var post: Post? = null

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            createIntent.consumeAsFlow().collect{
                when (it){
                    is CreateIntent.CreatePost -> createPost()
                }
            }
        }
    }

    private fun createPost(){
        viewModelScope.launch {
            _state.value = CreateState.Loading
            _state.value = try {
                CreateState.CreatePost(repository.createPost(post!!))
            }catch (e: Exception){
                CreateState.Error(e.localizedMessage)
            }
        }
    }

}