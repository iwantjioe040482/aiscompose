package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.Model.COAItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.TransactionApi

class COAIncomeViewModel: ViewModel()  {

private val _coaList = MutableStateFlow<List<COAItem>>(emptyList())
val coaList: StateFlow<List<COAItem>> get() = _coaList

private val api: TransactionApi

init {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    api = retrofit.create(TransactionApi::class.java)
    fetchCOAIncomes()
}
fun fetchCOAIncomes() {
    viewModelScope.launch {
        try {
            val data = api.getCOAIncome()
            _coaList.value = data

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
}
