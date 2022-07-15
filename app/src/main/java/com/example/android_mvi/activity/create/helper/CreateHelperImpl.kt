package com.example.android_mvi.activity.create.helper

import com.example.android_mvi.model.Post
import com.example.android_mvi.network.service.PostService


class CreateHelperImpl(private val postService: PostService): CreateHelper {

    override suspend fun createPost(post: Post): Post {
        return postService.createPost(post)
    }

}