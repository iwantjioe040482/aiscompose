package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.ElectricityApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class ElectricityViewModel : ViewModel() {
    private val _electricityList = MutableStateFlow<List<ElectricityView>>(emptyList())
    val electricityList: StateFlow<List<ElectricityView>> get() = _electricityList

    private val _data = mutableStateOf<List<Double>>(emptyList())
    val electricityValues: State<List<Double>> =  _data

    val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val api: ElectricityApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ElectricityApi::class.java)
        fetchElectricity()
        fetchElectricityBill()

    }

    fun fetchElectricityBill() {
        viewModelScope.launch {
            try {
                val response = api.getEstElectricBill() // misal return List<ElectricityEstimate>
                _balance.value = response.firstOrNull()?.electricbillest ?: 0.0

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchElectricity() {
        viewModelScope.launch {
            try {
                val data = api.getElectricityList()
                _electricityList.value = data
                _data.value = data.map { it.difference }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun submitElectricity(electricity: Electricity) {
        viewModelScope.launch {
            try {
                val data = api.submitElectricity(electricity)
                fetchElectricity()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}