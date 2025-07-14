package com.arcadia.aiscompose.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.ElectricityRepository
import com.arcadia.aiscompose.Repository.WaterRepository
import com.arcadia.aiscompose.WaterApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WaterViewModel : ViewModel() {
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    //private val _waterList = MutableStateFlow<List<WaterView>>(emptyList())
    //val waterList: StateFlow<List<WaterView>> get() = _waterList

    private val _waterList = mutableStateListOf<WaterView>()
    val waterList: List<WaterView> get() = _waterList

//    val _balance = MutableStateFlow(0.0)
//    val balance: StateFlow<Double> = _balance

    private val _balance = mutableStateListOf(0.0)
    val balance: List<Double> get() = _balance
//    private val api: WaterApi

    init {

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        api = retrofit.create(WaterApi::class.java)

        fetchWater()
        fetchWaterBill()
    }

    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun fetchWaterBill() {
        viewModelScope.launch {
            try {
                //val response = api.getEstWaterBill() // misal return List<ElectricityEstimate>
                //_balance.value = response.firstOrNull()?.waterbillest ?: 0.0
                val data = WaterRepository.getWaterBalance(token.value)
                _balance.clear()          // 🔴 Hapus data lama
                _balance.addAll (data.map { it.waterbillest })

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWater() {
        Log.d("WaterViewModel", "fetchWater token: $token")
        viewModelScope.launch {
            try {
                val data = WaterRepository.getWater(token.value)
                Log.d("WaterDebug", "Fetched: ${data.size} data")
                _waterList.clear()          // 🔴 Hapus data lama
                _waterList.addAll (data)

                //val data = api.getWaterList()
                //_waterList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("WaterDebug", "Error fetchWater  ${e.message}")
            }
        }
    }

    fun submitWater(water: Water) {
        viewModelScope.launch {
            try {

                //val data = api.submitWater(water)
                val data = WaterRepository.submitWater(token.value,water)

                fetchWater()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}