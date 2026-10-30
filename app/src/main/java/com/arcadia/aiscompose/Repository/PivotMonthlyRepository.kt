package com.arcadia.aiscompose.Repository

import android.util.Log
import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.LogoutRequest
import com.arcadia.aiscompose.Model.MonthlyPivot
import com.arcadia.aiscompose.TransactionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PivotMonthlyRepository {
    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
    }

    suspend fun getPivot(token: String): List<MonthlyPivot> {
        Log.d("PivotReport", "token in rep: ${token}")

        return api.getPivot("Bearer $token")
    }

    suspend fun getGroupExpenseMonthlyPivot(token: String): List<MonthlyPivot> {
        Log.d("PivotReport", "token in rep: ${token}")

        return api.getGroupExpenseMonthlyPivot("Bearer $token")
    }

    suspend fun getCashFlow(token: String): List<MonthlyPivot> {
        Log.d("CashflowReport", "token in rep: ${token}")
        return api.getCashFlow("Bearer $token")
    }


    suspend fun getIncomePivot(token: String): List<MonthlyPivot> {
        Log.d("PivotReport", "token in rep: ${token}")

        return api.getIncomePivot("Bearer $token")
    }
}