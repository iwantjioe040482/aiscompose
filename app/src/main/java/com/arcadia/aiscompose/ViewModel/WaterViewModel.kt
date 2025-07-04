package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.WaterApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WaterViewModel : ViewModel() {
    private val _waterList = MutableStateFlow<List<WaterView>>(emptyList())
    val waterList: StateFlow<List<WaterView>> get() = _waterList

    val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val api: WaterApi

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(WaterApi::class.java)

        fetchWater()
        fetchWaterBill()
    }

    fun fetchWaterBill() {
        viewModelScope.launch {
            try {
                val response = api.getEstWaterBill() // misal return List<ElectricityEstimate>
                _balance.value = response.firstOrNull()?.waterbillest ?: 0.0


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWater() {
        viewModelScope.launch {
            try {
                val data = api.getWaterList()
                _waterList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun submitWater(water: Water) {
        viewModelScope.launch {
            try {
                val data = api.submitWater(water)
                fetchWater()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}