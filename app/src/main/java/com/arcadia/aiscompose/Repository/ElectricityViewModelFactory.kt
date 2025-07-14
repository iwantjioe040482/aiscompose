package com.arcadia.aiscompose.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel

class ElectricityViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectricityViewModel::class.java)) {
            return ElectricityViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}