package com.example.android_mvi.activity.main.intentstate

import com.example.android_mvi.model.Post

sealed class MainState {
    object Init : MainState()
    object Lading : MainState()

    data class AllPost(val posts: ArrayList<Post>): MainState()
    data class DeletePost(val post: Post) : MainState()

    data class Error(val error: String?): MainState()
}