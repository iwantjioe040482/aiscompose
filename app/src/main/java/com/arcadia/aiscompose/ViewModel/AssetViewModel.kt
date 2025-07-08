package com.arcadia.aiscompose.ViewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.AssetApi
import com.arcadia.aiscompose.Model.Assets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  AssetViewModel  : ViewModel() {
    private val _transactionList = MutableStateFlow<List<Assets>>(emptyList())
    val transactionList: StateFlow<List<Assets>> get() = _transactionList


    private val api: AssetApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AssetApi::class.java)
        fetchAsset()
    }


    fun fetchAsset() {
        viewModelScope.launch {
            try {
                val data = api.getAssetList()
                _transactionList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}