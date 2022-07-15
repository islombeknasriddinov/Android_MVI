package com.example.android_mvi.activity.detail.intentstate

import com.example.android_mvi.model.Post


sealed class DetailState {
    object Init: DetailState()
    object Loading: DetailState()

    data class Error(val error: String?): DetailState()

    data class DetailPost(val post: Post): DetailState()
}
