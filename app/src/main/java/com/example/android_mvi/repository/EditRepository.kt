package com.example.android_mvi.repository

import com.example.android_mvi.activity.edit.helper.EditHelper
import com.example.android_mvi.model.Post

class EditRepository(private val editHelper: EditHelper) {
    suspend fun editPost(post: Post) = editHelper.editPost(post)
}