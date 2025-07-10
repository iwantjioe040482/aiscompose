package com.arcadia.aiscompose

import retrofit2.http.*
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Model.Transaction
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Model.BalanceResponse
import com.arcadia.aiscompose.Model.MonthlyPivot
import com.arcadia.aiscompose.Model.TaxItem
import com.arcadia.aiscompose.Model.InsuranceItem
import com.arcadia.aiscompose.Model.CreditCardItem
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.Expense
import com.arcadia.aiscompose.Model.TransferRequest
import com.arcadia.aiscompose.Model.TransferResponse

interface TransactionApi {
    @GET("COAExpense")
    suspend fun getCOAList(): List<COAItem>

    @GET("COAIncome")
    suspend fun getCOAIncome(): List<COAItem>

    @GET("tax")
    suspend fun getTax(): List<TaxItem>

    @GET("creditcard")
    suspend fun getCreditCard(): List<CreditCardItem>

    @GET("insurance")
    suspend fun getInsurance(): List<InsuranceItem>

    @GET("pivot")
    suspend fun getPivot(): List<MonthlyPivot>

    @GET("expense")
    suspend fun getExpense(): List<TransactionView>

    @GET("dailyexpense")
    suspend fun getDailyExpense(): List<DailyReport>

    @GET("monthlyexpense")
    suspend fun getExpenseReport(): List<Expense>

    @GET("balance/{coaId}")
    suspend fun getBalanceByCOA(@Path("coaId") id: String) : List<BalanceResponse>

    @POST("allobankincome")
    suspend fun submitAlloBankIncome(@Body expense: Transaction)

    @POST("allobankexpense")
    suspend fun submitAlloBankExpense(@Body expense: Transaction)

    @POST("bcaexpense")
    suspend fun submitBCAExpense(@Body expense: Transaction)

    @POST("bcaincome")
    suspend fun submitBCAIncome(@Body expense: Transaction)

    @POST("/transfer")
    suspend fun postTransfer(@Body request: TransferRequest): TransferResponse

    @POST("cashexpense")
    suspend fun submitCashExpense(@Body expense: Transaction)

    @POST("cashincome")
    suspend fun submitCashIncome(@Body expense: Transaction)

    @POST("cimbincome")
    suspend fun submitCIMBIncome(@Body expense: Transaction)

    @POST("cimbexpense")
    suspend fun submitCIMBExpense(@Body expense: Transaction)

    @POST("danaincome")
    suspend fun submitDanaIncome(@Body expense: Transaction)

    @POST("danaexpense")
    suspend fun submitDanaExpense(@Body expense: Transaction)

    @POST("flazzexpense")
    suspend fun submitFlazzExpense(@Body expense: Transaction)

    @POST("gopayexpense")
    suspend fun submitGoPayExpense(@Body expense: Transaction)

    @POST("gopayincome")
    suspend fun submitGoPayIncome(@Body expense: Transaction)

    @POST("hanaexpense")
    suspend fun submitHanaExpense(@Body expense: Transaction)

    @POST("hanaincome")
    suspend fun submitHanaIncome(@Body expense: Transaction)

    @POST("mandiriincome")
    suspend fun submitMandiriIncome(@Body expense: Transaction)

    @POST("mandiriexpense")
    suspend fun submitMandiriExpense(@Body expense: Transaction)

    @POST("octoincome")
    suspend fun submitOctoIncome(@Body expense: Transaction)

    @POST("octoexpense")
    suspend fun submitOctoExpense(@Body expense: Transaction)

    @POST("ovoincome")
    suspend fun submitOvoIncome(@Body expense: Transaction)

    @POST("ovoexpense")
    suspend fun submitOvoExpense(@Body expense: Transaction)

    @POST("permataexpense")
    suspend fun submitPermataExpense(@Body expense: Transaction)

    @POST("permataincome")
    suspend fun submitPermataIncome(@Body expense: Transaction)

    @POST("shopeepayexpense")
    suspend fun submitShopeeExpense(@Body expense: Transaction)


}