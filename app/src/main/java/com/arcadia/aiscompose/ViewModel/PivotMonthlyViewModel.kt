package com.arcadia.aiscompose.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.arcadia.aiscompose.Model.MonthlyPivot
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.TransactionApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.Model.DynamicPivot
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Repository.PivotMonthlyRepository
import com.arcadia.aiscompose.Repository.PivotMonthlyViewModelFactory
import com.arcadia.aiscompose.Repository.TransactionRepository
import kotlinx.coroutines.flow.asStateFlow

class PivotMonthlyViewModel() : ViewModel(){
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

//    val pivotList = MutableStateFlow<List<MonthlyPivot>>(emptyList())
//    val dynamiclist=MutableStateFlow<List<DynamicPivot>>(emptyList())

    val pivotList = mutableStateListOf<List<MonthlyPivot>>()
    //val pivotList = mutableStateListOf<List<MonthlyPivot>>(emptyList())
    //val dynamiclist=mutableStateListOf<DynamicPivot>()

    private val _dynamiclist = mutableStateListOf<DynamicPivot>()
    val dynamiclist: List<DynamicPivot> get() = _dynamiclist


    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
        //fetchPivot()
    }

    //fun parseJsonToPivot(jsonList: List<Map<String, String>>): List<DynamicPivot> {
    //    return jsonList.map { row ->
    //        val coaName = row["coa_name"] ?: ""
    //        val values = row
    //            .filterKeys { it != "coa_name" }
    //            .mapValues { (_, v) -> v.replace(",", "").toFloatOrNull() ?: 0f }

    //        DynamicPivot(coaName = coaName, values = values)
    //    }
    //}

    fun setToken(newToken: String) {
        _token.value = newToken
    }
    fun parseFloatSafe(value: String): Float {
        return value.trimStart('0').toFloatOrNull() ?: 0f
    }
    fun parseJsonToPivot(jsonList: List<MonthlyPivot>): List<DynamicPivot> {
//fun parseJsonToPivot(jsonList: List<Map<String, String>>): List<DynamicPivot> {
//    return jsonList.map { row ->
//        val coaName = row["coa_name"] ?: ""
//        val values = row
//            .filterKeys { it != "coa_name" }
//            .mapValues { (_, v) -> v.replace(",", "").toFloatOrNull() ?: 0f }
//
//        DynamicPivot(coaName = coaName, values = values)
//    }
        return jsonList.map { row ->
            val values = buildMap {
                row.jan?.let { put("Jan", parseFloatSafe(it)) }
                row.feb?.let { put("Feb",  parseFloatSafe(it)) }
                row.mar?.let { put("Mar",  parseFloatSafe(it)) }
                row.apr?.let { put("Apr",  parseFloatSafe(it)) }
                row.may?.let { put("May",  parseFloatSafe(it)) }
                row.jun?.let { put("Jun",  parseFloatSafe(it)) }
                row.jul?.let { put("Jul",  parseFloatSafe(it)) }
                row.aug?.let { put("Aug",  parseFloatSafe(it)) }
                row.sep?.let { put("Sep",  parseFloatSafe(it)) }
                row.oct?.let { put("Oct",  parseFloatSafe(it)) }
                row.nov?.let { put("Nov",  parseFloatSafe(it)) }
                row.dec?.let { put("Dec",  parseFloatSafe(it)) }

//                row.jan?.let { put("Jan", it.toFloatOrNull() ?: 0f) }
//                row.feb?.let { put("Feb", it.toFloatOrNull() ?: 0f) }
//                row.mar?.let { put("Mar", it.toFloatOrNull() ?: 0f) }
//                row.apr?.let { put("Apr", it.toFloatOrNull() ?: 0f) }
//                row.may?.let { put("May", it.toFloatOrNull() ?: 0f) }
//                row.jun?.let { put("Jun", it.toFloatOrNull() ?: 0f) }
//                row.jul?.let { put("Jul", it.toFloatOrNull() ?: 0f) }
//                row.aug?.let { put("Aug", it.toFloatOrNull() ?: 0f) }
//                row.sep?.let { put("Sep", it.toFloatOrNull() ?: 0f) }
//                row.oct?.let { put("Oct", it.toFloatOrNull() ?: 0f) }
//                row.nov?.let { put("Nov", it.toFloatOrNull() ?: 0f) }
//                row.dec?.let { put("Dec", it.toFloatOrNull() ?: 0f) }
            }
            DynamicPivot(coaName = row.coa_name, values = values)
        }
    }

    fun fetchPivot() {
        viewModelScope.launch {
//            val response = api.getPivot() // ambil dari API/Room
//            val parsedList = parseJsonToPivot(response)
//            dynamiclist.value= parsedList
            Log.d("PivotReport", "token: ${token.value}")

            val data = PivotMonthlyRepository.getPivot(token.value)
            Log.d("PivotReport", "data size: ${data.size}")
            val parsedList = parseJsonToPivot(data)
            Log.d("PivotReport", "parsedList size: ${parsedList.size}")

            _dynamiclist.clear()          // 🔴 Hapus data lama
            _dynamiclist.addAll (parsedList)

            pivotList.clear()
            pivotList.add(data)
            //pivotList.value = response
        }
    }
}

