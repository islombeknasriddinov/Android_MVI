package com.example.android_mvi.activity.edit.intentstate

import com.example.android_mvi.model.Post

sealed class EditState {
    object Init: EditState()
    object Loading: EditState()

    data class Error(val error: String?): EditState()

    data class EditPost(val post: Post): EditState()
}
