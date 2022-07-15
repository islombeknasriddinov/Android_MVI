package com.example.android_mvi.activity.detail.helper

import com.example.android_mvi.model.Post


interface DetailHelper {
    suspend fun detailPost(id: Int) : Post
}