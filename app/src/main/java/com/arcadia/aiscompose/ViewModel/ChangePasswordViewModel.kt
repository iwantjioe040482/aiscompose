package com.arcadia.aiscompose.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arcadia.aiscompose.AuthApi
import com.arcadia.aiscompose.Model.ChangePass
import com.arcadia.aiscompose.TransactionApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.State

class  ChangePasswordViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _shouldLogout = mutableStateOf(false)
    val shouldLogout: State<Boolean> = _shouldLogout

    val errorMessage = mutableStateOf<String?>(null)
    val successMessage = mutableStateOf<String?>(null)
    private val api: AuthApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AuthApi::class.java)
        //fetchCOAExpense()
    }
//    fun resetMessages() {
//        _errorMessage.value = ""
//        _successMessage.value = ""
//    }
    fun setToken(newToken: String) {
        _token.value = newToken
    }
    fun triggerLogout() {
        _shouldLogout.value = true
    }
    fun changePassword(current: String, new: String, confirm: String) {
        viewModelScope.launch {
            try {
                if (new.length < 6) {
                    errorMessage.value = "Password too short"
                    return@launch
                }
                Log.d("ChangePassword", "token : ${token.value}")
                // TODO: Ganti dengan panggilan API
                val response = api.changepass("Bearer ${token.value}", ChangePass(current, new))


                delay(1000)
                Log.d("ChangePassword", "response code : ${response.code()}")
                if (response.code()==200)
                {
                    successMessage.value = "Password changed successfully"
                    errorMessage.value = null
                    triggerLogout()
                }
                else
                {
                    errorMessage.value = "Password lama tidak cocok"
                    successMessage.value = null
                    return@launch
                }
            } catch (e: Exception) {
                errorMessage.value = "Failed: ${e.message}"
                successMessage.value = null
            }
        }
    }
}