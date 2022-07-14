package com.example.android_mvi.activity.main.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mvi.activity.main.intentstate.MainIntent
import com.example.android_mvi.activity.main.intentstate.MainState
import com.example.android_mvi.repository.PostRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: PostRepository): ViewModel() {

    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Init)
    val state: StateFlow<MainState> get() = _state

    var postId: Int = 0

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect{
                when(it){
                    is MainIntent.AllPosts -> apiAlPosts()
                    is MainIntent.DeletePost -> apiDeletePost()
                }
            }
        }
    }

    private fun apiDeletePost() {
        viewModelScope.launch {
            _state.value = MainState.Lading
            _state.value = try {
                MainState.DeletePost(repository.deletePost(postId))
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }

    private fun apiAlPosts() {
        viewModelScope.launch {
            _state.value = MainState.Lading
            _state.value = try {
                MainState.AllPost(repository.allPosts())
            }catch (e: Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }

}