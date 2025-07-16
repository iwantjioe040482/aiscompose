package com.arcadia.aiscompose.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadia.aiscompose.ViewModel.COAViewModel
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel

class COAViewModelFactory (private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(COAViewModel::class.java)) {
            return COAViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}