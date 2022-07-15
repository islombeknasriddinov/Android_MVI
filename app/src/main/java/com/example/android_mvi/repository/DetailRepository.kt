package com.example.android_mvi.repository

import com.example.android_mvi.activity.detail.helper.DetailHelper

class DetailRepository(private val detailHelper: DetailHelper) {
    suspend fun detailPost(id: Int) = detailHelper.detailPost(id)
}