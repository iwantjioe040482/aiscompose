package com.arcadia.aiscompose.ViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.arcadia.aiscompose.Model.MonthlyPivot
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.TransactionApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.Model.DynamicPivot

class PivotMonthlyViewModel() : ViewModel(){
    val pivotList = MutableStateFlow<List<MonthlyPivot>>(emptyList())
    val dynamiclist=MutableStateFlow<List<DynamicPivot>>(emptyList())
    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
        fetchPivot()
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

    fun parseJsonToPivot(jsonList: List<MonthlyPivot>): List<DynamicPivot> {
        return jsonList.map { row ->
            val values = buildMap {
                row.jan?.let { put("Jan", it.toFloatOrNull() ?: 0f) }
                row.feb?.let { put("Feb", it.toFloatOrNull() ?: 0f) }
                row.mar?.let { put("Mar", it.toFloatOrNull() ?: 0f) }
                row.apr?.let { put("Apr", it.toFloatOrNull() ?: 0f) }
                row.may?.let { put("May", it.toFloatOrNull() ?: 0f) }
                row.jun?.let { put("Jun", it.toFloatOrNull() ?: 0f) }
                row.jul?.let { put("Jul", it.toFloatOrNull() ?: 0f) }
                row.aug?.let { put("Aug", it.toFloatOrNull() ?: 0f) }
                row.sep?.let { put("Sep", it.toFloatOrNull() ?: 0f) }
                row.oct?.let { put("Oct", it.toFloatOrNull() ?: 0f) }
                row.nov?.let { put("Nov", it.toFloatOrNull() ?: 0f) }
                row.dec?.let { put("Dec", it.toFloatOrNull() ?: 0f) }
            }
            DynamicPivot(coaName = row.coa_name, values = values)
        }
    }

    fun fetchPivot() {
        viewModelScope.launch {
            val response = api.getPivot() // ambil dari API/Room
            val parsedList = parseJsonToPivot(response)
            dynamiclist.value= parsedList
            //pivotList.value = response
        }
    }
}

