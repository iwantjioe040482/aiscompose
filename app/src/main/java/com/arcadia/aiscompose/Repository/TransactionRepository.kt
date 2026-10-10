package com.arcadia.aiscompose.Repository

import com.arcadia.aiscompose.Model.BalanceResponse
import com.arcadia.aiscompose.Model.COAAccess
import com.arcadia.aiscompose.Model.COAItem
import com.arcadia.aiscompose.Model.CoaUserPostDTO
import com.arcadia.aiscompose.Model.CreditCardItem
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.Expense
import com.arcadia.aiscompose.Model.Income
import com.arcadia.aiscompose.Model.InsuranceItem
import com.arcadia.aiscompose.Model.MaintenanceItem
import com.arcadia.aiscompose.Model.PriorityItem
import com.arcadia.aiscompose.Model.TaxItem
import com.arcadia.aiscompose.Model.Transaction
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Model.TransferRequest
import com.arcadia.aiscompose.Model.TransferResponse
import com.arcadia.aiscompose.Model.TransactionWithType
import com.arcadia.aiscompose.TransactionApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TransactionRepository {
    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://8.222.205.20:3000/") // ganti dengan URL kamu
            .baseUrl("https://tosft.my.id/") // ganti dengan URL kamu
            //.addInterceptor(AuthInterceptor(tokenState, onLogout))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
    }

    suspend fun getBalance(token: String,coaId: String): List<BalanceResponse> {
        return api.getBalanceByCOA("Bearer $token",coaId)
//        return api.getBalanceByCOA("Bearer $token",coaId,).map { it.Balance }
    }

    suspend fun getExpense(token: String): List<TransactionView> {
        return api.getExpense("Bearer $token")
    }

    suspend fun getExpenseByCoa(token: String,coaId: String): List<TransactionView> {
        return api.getExpenseByCoa("Bearer $token",coaId)
    }

    suspend fun getIncome(token: String): List<TransactionView> {
        return api.getIncome("Bearer $token")
    }

    suspend fun getInsurance(token: String): List<InsuranceItem> {
        return api.getInsurance("Bearer $token")
    }

    suspend fun getCreditCard(token: String): List<CreditCardItem> {
        return api.getCreditCard("Bearer $token")
    }

    suspend fun getMaintenance(token: String): List<MaintenanceItem> {
        return api.getMaintenance("Bearer $token")
    }

    suspend fun getGroupExpense(token: String): List<Expense> {
        return api.getGroupExpense("Bearer $token")
    }

    suspend fun getPriorityReport(token: String): List<PriorityItem> {
        return api.getPriorityReport("Bearer $token")
    }

    suspend fun getTax(token: String): List<TaxItem> {
        return api.getTax("Bearer $token")
    }

    suspend fun getDailyExpense(token: String): List<DailyReport> {
        return api.getDailyExpense("Bearer $token")
    }

    suspend fun getCOAList(token: String): List<COAItem> {
        return api.getCOAList("Bearer $token")
    }

    suspend fun getCOATrans(token: String): List<COAItem> {
        return api.getCOATrans("Bearer $token")
    }

    suspend fun getCOAAll(token: String): List<COAItem> {
        return api.getCOAAll("Bearer $token")
    }

    suspend fun getCOAIncome(token: String): List<COAItem> {
        return api.getCOAIncome("Bearer $token")
    }

    suspend fun postTransfer(token: String, request: TransferRequest):List<TransferResponse?> {
        return api.postTransfer("Bearer $token",request)
    }

    suspend fun getExpenseReport(token: String):List<Expense> {
        return api.getExpenseReport("Bearer $token")
    }

    suspend fun getIncomeReport(token: String):List<Income> {
        return api.getIncomeReport("Bearer $token")
    }

    suspend fun submitExpenses(token: String, type: String, expense: Transaction) {
        return api.submitExpenses("Bearer $token", TransactionWithType(type, expense))
    }

    suspend fun submitIncomes(token: String, type: String, expense: Transaction) {
        return api.submitIncomes("Bearer $token", TransactionWithType(type, expense))
    }

    suspend fun submitAlloBankExpense(token: String,expense: Transaction) {
        return api.submitAlloBankExpense("Bearer $token",expense)
    }

    suspend fun submitBCAExpense(token: String,expense: Transaction) {
        return api.submitBCAExpense("Bearer $token",expense)
    }

    suspend fun submitCashExpense(token: String,expense: Transaction) {
        return api.submitCashExpense("Bearer $token",expense)
    }

    suspend fun submitCIMBExpense(token: String,expense: Transaction) {
        return api.submitCIMBExpense("Bearer $token",expense)
    }

    suspend fun submitDanaExpense(token: String,expense: Transaction) {
        return api.submitDanaExpense("Bearer $token", expense)
    }

    suspend fun submitFlazzExpense(token: String,expense: Transaction) {
        return api.submitFlazzExpense("Bearer $token",expense)
    }

    suspend fun submitGoPayExpense(token: String,expense: Transaction) {
        return api.submitGoPayExpense("Bearer $token",expense)
    }

    suspend fun submitHanaExpense(token: String,expense: Transaction) {
        return api.submitHanaExpense("Bearer $token",expense)
    }

    suspend fun submitMandiriExpense(token: String,expense: Transaction) {
        return api.submitMandiriExpense("Bearer $token",expense)
    }

    suspend fun submitOctoExpense(token: String,expense: Transaction) {
        return api.submitOctoExpense("Bearer $token",expense)
    }

    suspend fun submitOvoExpense(token: String,expense: Transaction) {
        return api.submitOvoExpense("Bearer $token",expense)
    }

    suspend fun submitPermataExpense(token: String,expense: Transaction) {
        return api.submitPermataExpense("Bearer $token",expense)
    }

    suspend fun submitShopeeExpense(token: String,expense: Transaction) {
        return api.submitShopeeExpense("Bearer $token",expense)
    }

    suspend fun submitAlloBankIncome(token: String,expense: Transaction) {
        return api.submitAlloBankIncome("Bearer $token",expense)
    }

    suspend fun submitBCAIncome(token: String,expense: Transaction) {
        return api.submitBCAIncome("Bearer $token",expense)
    }

    suspend fun submitCashIncome(token: String,expense: Transaction) {
        return api.submitCashIncome("Bearer $token",expense)
    }

    suspend fun submitCIMBIncome(token: String,expense: Transaction) {
        return api.submitCIMBIncome("Bearer $token",expense)
    }

    suspend fun submitDanaIncome(token: String,expense: Transaction) {
        return api.submitDanaIncome("Bearer $token",expense)
    }

    suspend fun submitGoPayIncome(token: String,expense: Transaction) {
        return api.submitGoPayIncome("Bearer $token",expense)
    }

    suspend fun submitHanaIncome(token: String,expense: Transaction) {
        return api.submitHanaIncome("Bearer $token",expense)
    }

    suspend fun submitMandiriIncome(token: String,expense: Transaction) {
        return api.submitMandiriIncome("Bearer $token",expense)
    }

    suspend fun submitOctoIncome(token: String,expense: Transaction) {
        return api.submitOctoIncome("Bearer $token",expense)
    }

    suspend fun submitOvoIncome(token: String,expense: Transaction) {
        return api.submitOvoIncome("Bearer $token",expense)
    }

    suspend fun submitPermataIncome(token: String,expense: Transaction) {
        return api.submitPermataIncome("Bearer $token",expense)
    }
    suspend fun getCOAAceess(token: String): List<COAAccess> {
        return api.getCOAAccess("Bearer $token")
    }
    suspend fun saveCOAAccess(token: String,selectedItems: List<CoaUserPostDTO>) {
        // Ganti dengan panggilan ke API atau Room Database Anda
        // Misal pakai Retrofit:
        api.submitCOAUser("Bearer $token",selectedItems)
    }
}