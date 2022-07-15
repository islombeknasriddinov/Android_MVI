package com.example.android_mvi.activity.detail.helper

import com.example.android_mvi.model.Post
import com.example.android_mvi.network.service.PostService


class DetailHelperImpl(private val postService: PostService): DetailHelper {

    override suspend fun detailPost(id: Int): Post {
        return postService.singlePost(id)
    }

}