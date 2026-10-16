package com.arcadia.aiscompose

import retrofit2.http.*
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Model.Transaction
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Model.BalanceResponse
import com.arcadia.aiscompose.Model.COAAccess
import com.arcadia.aiscompose.Model.CoaUserPostDTO
import com.arcadia.aiscompose.Model.MonthlyPivot
import com.arcadia.aiscompose.Model.TaxItem
import com.arcadia.aiscompose.Model.InsuranceItem
import com.arcadia.aiscompose.Model.CreditCardItem
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.Expense
import com.arcadia.aiscompose.Model.Income
import com.arcadia.aiscompose.Model.MaintenanceItem
import com.arcadia.aiscompose.Model.PriorityItem
import com.arcadia.aiscompose.Model.TransactionWithType
import com.arcadia.aiscompose.Model.TransferRequest
import com.arcadia.aiscompose.Model.TransferResponse

interface TransactionApi {
    @GET("COAExpense")
    suspend fun getCOAList(@Header("Authorization") token: String): List<COAItem>

    @GET("COAAll")
    suspend fun getCOAAll(@Header("Authorization") token: String): List<COAItem>

    @GET("COATrans")
    suspend fun getCOATrans(@Header("Authorization") token: String): List<COAItem>

    @GET("COAIncome")
    suspend fun getCOAIncome(@Header("Authorization") token: String): List<COAItem>

    @GET("tax")
    suspend fun getTax(@Header("Authorization") token: String): List<TaxItem>

    @GET("creditcard")
    suspend fun getCreditCard(@Header("Authorization") token: String): List<CreditCardItem>

    @GET("maintenance")
    suspend fun getMaintenance(@Header("Authorization") token: String): List<MaintenanceItem>

    @GET("groupmonthlyexpense")
    suspend fun getGroupExpense(@Header("Authorization") token: String): List<Expense>

    @GET("groupmonthlyincome")
    suspend fun getGroupIncome(@Header("Authorization") token: String): List<Expense>

    @GET("priorityreport")
    suspend fun getPriorityReport(@Header("Authorization") token: String): List<PriorityItem>

    @GET("insurance")
    suspend fun getInsurance(@Header("Authorization") token: String): List<InsuranceItem>

    @GET("pivot")
    suspend fun getPivot(@Header("Authorization") token: String): List<MonthlyPivot>
    //suspend fun getPivot(@Header("Authorization") token: String): List<Map<String, String>>

    @GET("groupexpensemonthlypivot")
    suspend fun getGroupExpenseMonthlyPivot(@Header("Authorization") token: String): List<MonthlyPivot>
    //suspend fun getPivot(@Header("Authorization") token: String): List<Map<String, String>>

    @GET("cashflow")
    suspend fun getCashFlow(@Header("Authorization") token: String): List<MonthlyPivot>
    //suspend fun getPivot(@Header("Authorization") token: String): List<Map<String, String>>

    @GET("incomepivot")
    suspend fun getIncomePivot(@Header("Authorization") token: String): List<MonthlyPivot>

    @GET("expense")
    suspend fun getExpense(@Header("Authorization") token: String): List<TransactionView>

    @GET("expensebycoa/{coaId}")
    suspend fun getExpenseByCoa(@Header("Authorization") token: String,@Path("coaId") id: String): List<TransactionView>

    @GET("income")
    suspend fun getIncome(@Header("Authorization") token: String): List<TransactionView>

    @POST("access")
    suspend fun getCOAAccess(@Header("Authorization") token: String): List<COAAccess>

    @POST("submitaccess")
    suspend fun submitCOAUser(@Header("Authorization") token: String,@Body selectedItems: List<CoaUserPostDTO>)

    @GET("dailyexpense")
    suspend fun getDailyExpense(@Header("Authorization") token: String): List<DailyReport>

    @GET("monthlyexpense")
    suspend fun getExpenseReport(@Header("Authorization") token: String): List<Expense>

    @GET("monthlyincomes")
    suspend fun getIncomeReport(@Header("Authorization") token: String): List<Income>

    @GET("balance/{coaId}")
    suspend fun getBalanceByCOA(@Header("Authorization") token: String,@Path("coaId") id: String) : List<BalanceResponse>

    @POST("expenses")
    suspend fun submitExpenses(@Header("Authorization") token: String,@Body expense: TransactionWithType)

    @POST("incomes")
    suspend fun submitIncomes(@Header("Authorization") token: String,@Body expense: TransactionWithType)

    @POST("allobankincome")
    suspend fun submitAlloBankIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("allobankexpense")
    suspend fun submitAlloBankExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("bcaexpense")
    suspend fun submitBCAExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("bcaincome")
    suspend fun submitBCAIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("/transfer")
    suspend fun postTransfer(@Header("Authorization") token: String,@Body request: TransferRequest):List<TransferResponse?>

    @POST("cashexpense")
    suspend fun submitCashExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("cashincome")
    suspend fun submitCashIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("cimbincome")
    suspend fun submitCIMBIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("cimbexpense")
    suspend fun submitCIMBExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("danaincome")
    suspend fun submitDanaIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("danaexpense")
    suspend fun submitDanaExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("flazzexpense")
    suspend fun submitFlazzExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("gopayexpense")
    suspend fun submitGoPayExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("gopayincome")
    suspend fun submitGoPayIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("hanaexpense")
    suspend fun submitHanaExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("hanaincome")
    suspend fun submitHanaIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("mandiriincome")
    suspend fun submitMandiriIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("mandiriexpense")
    suspend fun submitMandiriExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("octoincome")
    suspend fun submitOctoIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("octoexpense")
    suspend fun submitOctoExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("ovoincome")
    suspend fun submitOvoIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("ovoexpense")
    suspend fun submitOvoExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("permataexpense")
    suspend fun submitPermataExpense(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("permataincome")
    suspend fun submitPermataIncome(@Header("Authorization") token: String,@Body expense: Transaction)

    @POST("shopeepayexpense")
    suspend fun submitShopeeExpense(@Header("Authorization") token: String,@Body expense: Transaction)

}