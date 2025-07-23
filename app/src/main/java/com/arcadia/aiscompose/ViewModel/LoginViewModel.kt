package com.arcadia.aiscompose.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.AuthApi
import com.arcadia.aiscompose.Model.LoginResponse
import com.arcadia.aiscompose.Model.LoginRequest
import com.arcadia.aiscompose.Model.RegisterRequest
import androidx.compose.runtime.mutableStateOf
import com.arcadia.aiscompose.Model.LogoutRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.ViewModel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableStateFlow<LoginResponse?>(null)
    val loginResult: StateFlow<LoginResponse?> = _loginResult
    //val loginResult by loginResult.collectAsStateWithLifecycle()
    var errorMessage = mutableStateOf<String?>(null)

    val token = loginResult.value?.token

    private val api: AuthApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AuthApi::class.java)
    }
    fun resetLoginState() {
        _loginResult.value = null
        errorMessage.value = null
    }
    fun logout(token:String)
    {
        viewModelScope.launch {
            try {
                Log.d("AssetViewModel", "Calling logout with token: $token")

                val response = api.logout(LogoutRequest(token))
//                if (response.isSuccessful) {
//                    _loginResult.value = response.body() // ✅ ubah _loginResult, bukan loginResult
//                } else {
//                    errorMessage.value = "Login gagal: ${response.code()}"
//                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
    fun login(username: String, password: String) {

        viewModelScope.launch {
            try {
                Log.d("AssetViewModel", "Calling login with token: $token")
                val response = api.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    _loginResult.value = response.body() // ✅ ubah _loginResult, bukan loginResult
                    Log.d("AssetViewModel", "Calling login get  token: ${_loginResult.value?.token}")
                } else {
                    errorMessage.value = "Login gagal: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
    fun register(username: String,password: String,name:String)
    {
        viewModelScope.launch {
            try {
                val registerData = RegisterRequest(
                    username = username,
                    password = password,
                    name = name
                )
                val response = api.register(registerData)

            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            }
        }
    }
}

