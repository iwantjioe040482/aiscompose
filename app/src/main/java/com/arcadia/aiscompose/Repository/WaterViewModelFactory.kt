package com.arcadia.aiscompose.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadia.aiscompose.ViewModel.WaterViewModel

class WaterViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}