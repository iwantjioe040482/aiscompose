package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class  ChangePasswordViewModel : ViewModel() {
    val errorMessage = mutableStateOf<String?>(null)
    val successMessage = mutableStateOf<String?>(null)

    fun changePassword(current: String, new: String, confirm: String) {
        viewModelScope.launch {
            try {
                if (new.length < 6) {
                    errorMessage.value = "Password too short"
                    return@launch
                }

                // TODO: Ganti dengan panggilan API

                delay(1000)
                successMessage.value = "Password changed successfully"
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Failed: ${e.message}"
                successMessage.value = null
            }
        }
    }
}