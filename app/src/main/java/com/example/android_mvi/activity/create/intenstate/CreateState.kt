package com.example.android_mvi.activity.create.intenstate

import com.example.android_mvi.model.Post

sealed class CreateState {
    object Init: CreateState()
    object Loading: CreateState()

    data class Error(val error: String?): CreateState()

    data class CreatePost(val post: Post): CreateState()
}
