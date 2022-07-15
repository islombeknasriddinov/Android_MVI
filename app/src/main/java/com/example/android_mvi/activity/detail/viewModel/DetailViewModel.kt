package com.example.android_mvi.activity.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mvi.activity.detail.intentstate.DetailIntent
import com.example.android_mvi.activity.detail.intentstate.DetailState
import com.example.android_mvi.repository.DetailRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DetailRepository) : ViewModel() {

    val detailIntent = Channel<DetailIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<DetailState>(DetailState.Init)
    val state: StateFlow<DetailState> get() = _state

    var id: Int? = null

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            detailIntent.consumeAsFlow().collect{
                when (it){
                    is DetailIntent.DetailPost -> detailPost()
                }
            }
        }
    }

    private fun detailPost(){
        viewModelScope.launch {
            _state.value = DetailState.Loading
            _state.value = try {
                DetailState.DetailPost(repository.detailPost(id!!))
            }catch (e: Exception){
                DetailState.Error(e.localizedMessage)
            }
        }
    }

}