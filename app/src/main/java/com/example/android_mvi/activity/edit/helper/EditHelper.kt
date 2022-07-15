package com.example.android_mvi.activity.edit.helper

import com.example.android_mvi.model.Post

interface EditHelper {
    suspend fun editPost(post: Post) : Post
}