package com.arcadia.aiscompose.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.TransactionApi
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log

class COAViewModel  : ViewModel() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

//    private val _coaList = MutableStateFlow<List<COAItem>>(emptyList())
//    val coaList: StateFlow<List<COAItem>> get() = _coaList
    private val _coaList = mutableStateListOf<COAItem>()
    val coaList: List<COAItem> get() = _coaList

    private val _coatransList = mutableStateListOf<COAItem>()
    val coatransList: List<COAItem> get() = _coatransList

    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
        //fetchCOAExpense()
    }
    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun fetchCOAExpense() {
        viewModelScope.launch {
            try {
//                val data = api.getCOAList()
//                _coaList.value = data
                val data = TransactionRepository.getCOAList(token.value)
                _coaList.clear()          // 🔴 Hapus data lama
                _coaList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCOATrans() {
        viewModelScope.launch {
            try {
                val data = TransactionRepository.getCOATrans(token.value)
                Log.d("COA_TRANS", "size=${data.size}, names=${data.map { it.coa_name }}")
                _coatransList.clear()          // 🔴 Hapus data lama
                _coatransList.addAll (data)

            } catch (e: Exception) {
                Log.e("COA_TRANS", "error", e)
                e.printStackTrace()
            }
        }
    }
}