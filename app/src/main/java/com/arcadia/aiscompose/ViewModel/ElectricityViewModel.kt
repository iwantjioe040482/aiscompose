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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.ElectricityRepository
import com.arcadia.aiscompose.Repository.WaterRepository
import kotlinx.coroutines.flow.asStateFlow

class ElectricityViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    //private val _electricityList = MutableStateFlow<List<ElectricityView>>(emptyList())
    //val electricityList: StateFlow<List<ElectricityView>> get() = _electricityList

    private val _electricityList = mutableStateListOf<ElectricityView>()
    val electricityList: List<ElectricityView> get() = _electricityList

//    private val _data = mutableStateOf<List<Double>>(emptyList())
//    val electricityValues: State<List<Double>> =  _data

    private val _balance = mutableStateListOf(0.0)
    val balance: List<Double> get() = _balance

//    val _balance = MutableStateFlow(0.0)
//    val balance: StateFlow<Double> = _balance

    //private val api: ElectricityApi

    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        api = retrofit.create(ElectricityApi::class.java)
        fetchElectricity()
        fetchElectricityBill()

    }

    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun fetchElectricityBill() {
        viewModelScope.launch {
            try {
                //val response = api.getEstElectricBill() // misal return List<ElectricityEstimate>
                //_balance.value = response.firstOrNull()?.electricbillest ?: 0.0
                val data = ElectricityRepository.getElectricityBalance(token.value)
                _balance.clear()          // 🔴 Hapus data lama
                _balance.addAll (data.map { it.electricbillest })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchElectricity() {
        viewModelScope.launch {
            try {
                val data = ElectricityRepository.getElectricity(token.value)

                _electricityList.clear()          // 🔴 Hapus data lama
                _electricityList.addAll (data)
//                val data = api.getElectricityList()
//                _electricityList.value = data
//                _data.value = data.map { it.difference }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun submitElectricity(electricity: Electricity) {
        viewModelScope.launch {
            try {
                val data = ElectricityRepository.submitElectricity(token.value,electricity)
                fetchElectricity()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}