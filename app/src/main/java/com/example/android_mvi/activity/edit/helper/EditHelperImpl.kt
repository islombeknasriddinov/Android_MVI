package com.example.android_mvi.activity.edit.helper

import com.example.android_mvi.model.Post
import com.example.android_mvi.network.service.PostService

class EditHelperImpl(private val postService: PostService): EditHelper {
    override suspend fun editPost(post: Post): Post {
        return postService.updatePost(post.id, post)
    }
}