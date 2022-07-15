package com.example.android_mvi.repository

import com.example.android_mvi.activity.create.helper.CreateHelper
import com.example.android_mvi.model.Post

class CreateRepository(private val createHelper: CreateHelper) {
    suspend fun createPost(post: Post) = createHelper.createPost(post)
}