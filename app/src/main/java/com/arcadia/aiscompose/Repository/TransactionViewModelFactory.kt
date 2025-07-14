package com.arcadia.aiscompose.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadia.aiscompose.ViewModel.TransactionViewModel

class TransactionViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}