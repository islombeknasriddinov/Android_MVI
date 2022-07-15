package com.example.android_mvi.activity.create.intenstate

sealed class CreateIntent {
    object CreatePost: CreateIntent()
}