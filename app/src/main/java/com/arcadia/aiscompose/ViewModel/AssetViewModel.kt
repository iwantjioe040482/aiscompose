package com.arcadia.aiscompose.ViewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.AssetApi
import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.LogoutRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.Repository.AssetViewModelFactory
import com.arcadia.aiscompose.Repository.AssetRepository
import kotlinx.coroutines.flow.asStateFlow

class  AssetViewModel () : ViewModel() {
    //    private val _transactionList = MutableStateFlow<List<Assets>>(emptyList())
    //    val transactionList: StateFlow<List<Assets>> get() = _transactionList
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _transactionList = mutableStateListOf<Assets>()
    val transactionList: List<Assets> get() = _transactionList

    //private val api: AssetApi

    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        api = retrofit.create(AssetApi::class.java)
        fetchAsset()
    }

    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun fetchAsset() {
        viewModelScope.launch {
            try {
                //val result = AssetRepository.getAssets(token) // ganti sesuai fungsi repo
                Log.d("AssetViewModel", "Calling getAssets with token: $token")
                val data = AssetRepository.getAssets(token.value)
                //_transactionList.addAll(data)
                Log.d("AssetViewModel", "Assets fetched: ${data.size}")
                _transactionList.clear()          // 🔴 Hapus data lama
                _transactionList.addAll (data)
                //val data = api.getAssetList(LogoutRequest(token))
                //_transactionList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}