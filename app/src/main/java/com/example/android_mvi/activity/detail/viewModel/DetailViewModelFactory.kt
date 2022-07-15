package com.example.android_mvi.activity.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_mvi.activity.detail.helper.DetailHelper
import com.example.android_mvi.repository.DetailRepository
import java.lang.IllegalArgumentException

class DetailViewModelFactory(private val detailHelper: DetailHelper): ViewModelProvider.Factory {

    override fun<T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(DetailRepository(detailHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}