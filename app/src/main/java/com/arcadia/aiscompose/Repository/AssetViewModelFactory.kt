package com.arcadia.aiscompose.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadia.aiscompose.ViewModel.AssetViewModel

class AssetViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssetViewModel::class.java)) {
            return AssetViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}