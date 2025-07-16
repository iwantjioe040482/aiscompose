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

class COAIncomeViewModel: ViewModel()  {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

//private val _coaList = MutableStateFlow<List<COAItem>>(emptyList())
//val coaList: StateFlow<List<COAItem>> get() = _coaList

    private val _coaList = mutableStateListOf<COAItem>()
    val coaList: List<COAItem> get() = _coaList

private val api: TransactionApi

init {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    api = retrofit.create(TransactionApi::class.java)
    //fetchCOAIncomes()
}

fun setToken(newToken: String) {
    _token.value = newToken
}

fun fetchCOAIncomes() {
    viewModelScope.launch {
        try {
//            val data = api.getCOAIncome()
//            _coaList.value = data

            val data = TransactionRepository.getCOAIncome(token.value)
            _coaList.clear()          // 🔴 Hapus data lama
            _coaList.addAll (data)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
}
