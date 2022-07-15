package com.example.android_mvi.activity.create.helper

import com.example.android_mvi.model.Post

interface CreateHelper {
    suspend fun createPost(post: Post) : Post
}