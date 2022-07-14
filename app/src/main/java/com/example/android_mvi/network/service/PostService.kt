package com.example.android_mvi.network.service

import com.example.android_mvi.model.Post
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("posts")
    suspend fun allPosts(): ArrayList<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Post

}


