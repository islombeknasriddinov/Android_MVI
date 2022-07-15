package com.example.android_mvi.activity.edit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_mvi.activity.edit.helper.EditHelper
import com.example.android_mvi.repository.EditRepository
import java.lang.IllegalArgumentException

class EditViewModelFactory(private val editHelper: EditHelper): ViewModelProvider.Factory {

    override fun<T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(EditViewModel::class.java)){
            return EditViewModel(EditRepository(editHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}