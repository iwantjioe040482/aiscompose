package com.arcadia.aiscompose.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadia.aiscompose.Model.CreditCardItem
import com.arcadia.aiscompose.Model.DailyReport
import com.arcadia.aiscompose.Model.Electricity
import com.arcadia.aiscompose.Model.Expense
import com.arcadia.aiscompose.Model.InsuranceItem
import com.arcadia.aiscompose.Model.MonthlyPivot
import com.arcadia.aiscompose.Model.TransferRequest
import com.arcadia.aiscompose.Model.TransferResponse
import com.arcadia.aiscompose.Model.TaxItem
import com.arcadia.aiscompose.Model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.arcadia.aiscompose.TransactionApi
import com.arcadia.aiscompose.Model.TransactionView
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.TransactionRepository
import com.arcadia.aiscompose.Repository.WaterRepository
import kotlinx.coroutines.flow.asStateFlow

class TransactionViewModel  : ViewModel() {

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

//    private val _transactionList = MutableStateFlow<List<TransactionView>>(emptyList())
//    val transactionList: StateFlow<List<TransactionView>> get() = _transactionList

//    private val _expenseList = MutableStateFlow<List<Expense>>(emptyList())
//    val expenseList: StateFlow<List<Expense>> get() = _expenseList


//    private val _dailyexpenseList = mutableStateListOf<DailyReport>()
//    val dailyexpenseList: List<DailyReport> get() = _dailyexpenseList

//    private val _dailyexpenseList = MutableStateFlow<List<DailyReport>>(emptyList())
//    val dailyexpenseList: StateFlow<List<DailyReport>> get() = _dailyexpenseList

//    private val _taxList = MutableStateFlow<List<TaxItem>>(emptyList())
//    val taxList: StateFlow<List<TaxItem>> get() = _taxList

//    private val _creditcardList = MutableStateFlow<List<CreditCardItem>>(emptyList())
//    val creditcardList: StateFlow<List<CreditCardItem>> get() = _creditcardList

//    private val _insuranceList = MutableStateFlow<List<InsuranceItem>>(emptyList())
//    val insuranceList: StateFlow<List<InsuranceItem>> get() = _insuranceList

//    private val _balance = MutableStateFlow(0.0)
//    val balance: StateFlow<Double> = _balance

    private val _balance = mutableStateListOf<Double>()
    val balance: List<Double> get() = _balance

    private val _transactionList = mutableStateListOf<TransactionView>()
    val transactionList: List<TransactionView> get() = _transactionList

    private val _dailyexpenseList = mutableStateListOf<DailyReport>()
    val dailyexpenseList: List<DailyReport> get() = _dailyexpenseList

    private val _insuranceList = mutableStateListOf<InsuranceItem>()
    val insuranceList: List<InsuranceItem> get() = _insuranceList

    private val _creditcardList = mutableStateListOf<CreditCardItem>()
    val creditcardList: List<CreditCardItem> get() = _creditcardList

    private val _taxList = mutableStateListOf<TaxItem>()
    val taxList: List<TaxItem> get() = _taxList

    private val _transferResult = mutableStateListOf<TransferResponse?>(null)
    val transferResult: List<TransferResponse?> = _transferResult

    private val _expenseList = mutableStateListOf<Expense>()
    val expenseList: List<Expense> get() = _expenseList


//    private val _transferResult = MutableStateFlow<TransferResponse?>(null)
//    val transferResult: StateFlow<TransferResponse?> = _transferResult

    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
        fetchExpense()
    }

    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun fetchBalanceByCOA(coaId: String) {
        viewModelScope.launch {
            try {
                val data = TransactionRepository.getBalance(token.value,coaId)
                _balance.clear()          // 🔴 Hapus data lama
                _balance.addAll (data)

//                println("Fetching balance for COA: $coaId") // Tambahkan log ini
//                val result = api.getBalanceByCOA(coaId)
//                _balance.value =  result.firstOrNull()?.Balance ?: 0.0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchTax() {
        viewModelScope.launch {
            try {
//                val data = api.getTax()
//                _taxList.value = data
                val data = TransactionRepository.getTax(token.value)
                _taxList.clear()          // 🔴 Hapus data lama
                _taxList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCreditCard() {
        viewModelScope.launch {
            try {
//                val data = api.getCreditCard()
//                _creditcardList.value = data

                val data = TransactionRepository.getCreditCard(token.value)
                _creditcardList.clear()          // 🔴 Hapus data lama
                _creditcardList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchInsurance() {
        viewModelScope.launch {
            try {
//                val data = api.getInsurance()
//                _insuranceList.value = data
                val data = TransactionRepository.getInsurance(token.value)
                _insuranceList.clear()          // 🔴 Hapus data lama
                _insuranceList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchExpenseReport() {
        viewModelScope.launch {
            try {
//                val data = api.getExpenseReport()
//                //val filteredData = data.filter { it.coa_name == "Beban Bensin" }
//                //Log.d("ExpenseChartView", "Expenses size: ${filteredData.size}")
//                _expenseList.value = data
                val data = TransactionRepository.getExpenseReport(token.value)
                _expenseList.clear()          // 🔴 Hapus data lama
                _expenseList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun fetchDailyExpense() {
        viewModelScope.launch {
            try {
//                val data = api.getDailyExpense()
//                _dailyexpenseList.value = data
                val data = TransactionRepository.getDailyExpense(token.value)
                _dailyexpenseList.clear()          // 🔴 Hapus data lama
                _dailyexpenseList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchExpense() {
        viewModelScope.launch {
            try {
//                val data = api.getExpense()
//                _transactionList.value = data
                val data = TransactionRepository.getExpense(token.value)
                _transactionList.clear()          // 🔴 Hapus data lama
                _transactionList.addAll (data)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun submitTransfer(from: String,To: String, Amount : Double, onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {})
    {
        viewModelScope.launch {
            try {
                val data = TransactionRepository.getExpense(token.value)
                _transactionList.clear()          // 🔴 Hapus data lama
                _transactionList.addAll (data)

                val request = TransferRequest(from, To, Amount)
                val response =TransactionRepository.postTransfer(token.value,request)
                //_transferResult.value = response
                _transferResult.clear()
                _transferResult.addAll(response)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }

    }

    fun submitIncome(typetransaction: String,expense: Transaction, onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {})
    {
        viewModelScope.launch {
            try {
                when (typetransaction) {
                    "Allo Bank" -> TransactionRepository.submitAlloBankIncome(token.value,expense)
                    "BCA" -> TransactionRepository.submitBCAIncome(token.value,expense)
                    "Cash" -> TransactionRepository.submitCashIncome(token.value,expense)
                    "CIMB" -> TransactionRepository.submitCIMBIncome(token.value,expense)
                    "Dana" -> TransactionRepository.submitDanaIncome(token.value,expense)
                    "GoPay" -> TransactionRepository.submitGoPayIncome(token.value,expense)
                    "Hana" -> TransactionRepository.submitHanaIncome(token.value,expense)
                    "Mandiri" ->  TransactionRepository.submitMandiriIncome(token.value,expense)
                    "Octo" -> TransactionRepository.submitOctoIncome(token.value,expense)
                    "Ovo" -> TransactionRepository.submitOvoIncome(token.value,expense)
                    "Permata" -> TransactionRepository.submitPermataIncome(token.value,expense)

//                    "Allo Bank" -> api.submitAlloBankIncome(expense)
//                    "BCA" -> api.submitBCAIncome(expense)
//                    "Cash" -> api.submitCashIncome(expense)
//                    "CIMB" -> api.submitCIMBIncome(expense)
//                    "Dana" -> api.submitDanaIncome(expense)
//                    "GoPay" -> api.submitGoPayIncome(expense)
//                    "Hana" -> api.submitHanaIncome(expense)
//                    "Mandiri" ->  api.submitMandiriIncome(expense)
//                    "Octo" -> api.submitOctoIncome(expense)
//                    "Ovo" -> api.submitOvoIncome(expense)
//                    "Permata" -> api.submitPermataIncome(expense)
                    // Tambahkan jika ada lainnya
                    else -> throw IllegalArgumentException("Unknown transaction type: $typetransaction")
                }
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }

    }

    fun submitExpense(typetransaction: String,expense: Transaction, onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                when (typetransaction) {

                    "Allo Bank" -> TransactionRepository.submitAlloBankExpense(token.value,expense)
                    "BCA" -> TransactionRepository.submitBCAExpense(token.value,expense)
                    "Cash" -> TransactionRepository.submitCashExpense(token.value,expense)
                    "CIMB" -> TransactionRepository.submitCIMBExpense(token.value,expense)
                    "Dana" -> TransactionRepository.submitDanaExpense(token.value,expense)
                    "Flazz" -> TransactionRepository.submitFlazzExpense(token.value,expense)
                    "GoPay" -> TransactionRepository.submitGoPayExpense(token.value,expense)
                    "Hana" ->  TransactionRepository.submitHanaExpense(token.value,expense)
                    "Mandiri" ->  TransactionRepository.submitMandiriExpense(token.value,expense)
                    "Octo" -> TransactionRepository.submitOctoExpense(token.value,expense)
                    "Ovo" -> TransactionRepository.submitOvoExpense(token.value,expense)
                    "Permata" ->  TransactionRepository.submitPermataExpense(token.value,expense)
                    "ShopeePay" -> TransactionRepository.submitShopeeExpense(token.value,expense)

//                    "Allo Bank" -> api.submitAlloBankExpense(expense)
//                    "BCA" -> api.submitBCAExpense(expense)
//                    "Cash" -> api.submitCashExpense(expense)
//                    "CIMB" -> api.submitCIMBExpense(expense)
//                    "Dana" -> api.submitDanaExpense(expense)
//                    "Flazz" -> api.submitFlazzExpense(expense)
//                    "GoPay" -> api.submitGoPayExpense(expense)
//                    "Hana" ->  api.submitHanaExpense(expense)
//                    "Mandiri" ->  api.submitMandiriExpense(expense)
//                    "Octo" -> api.submitOctoExpense(expense)
//                    "Ovo" -> api.submitOvoExpense(expense)
//                    "Permata" ->  api.submitPermataExpense(expense)
//                    "ShopeePay" -> api.submitShopeeExpense(expense)

                    // Tambahkan jika ada lainnya
                    else -> throw IllegalArgumentException("Unknown transaction type: $typetransaction")
                }
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }



}