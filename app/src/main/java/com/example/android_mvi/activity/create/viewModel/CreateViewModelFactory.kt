package com.example.android_mvi.activity.create.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_mvi.activity.create.helper.CreateHelper
import com.example.android_mvi.repository.CreateRepository
import java.lang.IllegalArgumentException

class CreateViewModelFactory(private val createHelper: CreateHelper): ViewModelProvider.Factory {

    override fun<T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)){
            return CreateViewModel(CreateRepository(createHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}