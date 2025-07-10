package com.arcadia.aiscompose.ViewModel

import android.util.Log
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

class TransactionViewModel  : ViewModel() {
    private val _transactionList = MutableStateFlow<List<TransactionView>>(emptyList())
    val transactionList: StateFlow<List<TransactionView>> get() = _transactionList

    private val _expenseList = MutableStateFlow<List<Expense>>(emptyList())
    val expenseList: StateFlow<List<Expense>> get() = _expenseList

    private val _dailyexpenseList = MutableStateFlow<List<DailyReport>>(emptyList())
    val dailyexpenseList: StateFlow<List<DailyReport>> get() = _dailyexpenseList

    private val _taxList = MutableStateFlow<List<TaxItem>>(emptyList())
    val taxList: StateFlow<List<TaxItem>> get() = _taxList

    private val _creditcardList = MutableStateFlow<List<CreditCardItem>>(emptyList())
    val creditcardList: StateFlow<List<CreditCardItem>> get() = _creditcardList

    private val _insuranceList = MutableStateFlow<List<InsuranceItem>>(emptyList())
    val insuranceList: StateFlow<List<InsuranceItem>> get() = _insuranceList

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance

    private val _transferResult = MutableStateFlow<TransferResponse?>(null)
    val transferResult: StateFlow<TransferResponse?> = _transferResult

    private val api: TransactionApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://8.222.205.20:3000/") // Ganti URL ini
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TransactionApi::class.java)
        fetchExpense()
    }


    fun fetchBalanceByCOA(coaId: String) {
        viewModelScope.launch {
            try {
                println("Fetching balance for COA: $coaId") // Tambahkan log ini
                val result = api.getBalanceByCOA(coaId)
                _balance.value =  result.firstOrNull()?.Balance ?: 0.0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchTax() {
        viewModelScope.launch {
            try {
                val data = api.getTax()
                _taxList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCreditCard() {
        viewModelScope.launch {
            try {
                val data = api.getCreditCard()
                _creditcardList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchInsurance() {
        viewModelScope.launch {
            try {
                val data = api.getInsurance()
                _insuranceList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchExpenseReport() {
        viewModelScope.launch {
            try {
                val data = api.getExpenseReport()
                //val filteredData = data.filter { it.coa_name == "Beban Bensin" }
                //Log.d("ExpenseChartView", "Expenses size: ${filteredData.size}")
                _expenseList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun fetchDailyExpense() {
        viewModelScope.launch {
            try {
                val data = api.getDailyExpense()
                _dailyexpenseList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchExpense() {
        viewModelScope.launch {
            try {
                val data = api.getExpense()
                _transactionList.value = data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun submitTransfer(from: String,To: String, Amount : Double, onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {})
    {
        viewModelScope.launch {
            try {
                val request = TransferRequest(from, To, Amount)
                val response =api.postTransfer(request)
                _transferResult.value = response
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
                    "Allo Bank" -> api.submitAlloBankIncome(expense)
                    "BCA" -> api.submitBCAIncome(expense)
                    "Cash" -> api.submitCashIncome(expense)
                    "CIMB" -> api.submitCIMBIncome(expense)
                    "Dana" -> api.submitDanaIncome(expense)
                    "GoPay" -> api.submitGoPayIncome(expense)
                    "Hana" -> api.submitHanaIncome(expense)
                    "Mandiri" ->  api.submitMandiriIncome(expense)
                    "Octo" -> api.submitOctoIncome(expense)
                    "Ovo" -> api.submitOvoIncome(expense)
                    "Permata" -> api.submitPermataIncome(expense)
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
                    "Allo Bank" -> api.submitAlloBankExpense(expense)
                    "BCA" -> api.submitBCAExpense(expense)
                    "Cash" -> api.submitCashExpense(expense)
                    "CIMB" -> api.submitCIMBExpense(expense)
                    "Dana" -> api.submitDanaExpense(expense)
                    "Flazz" -> api.submitFlazzExpense(expense)
                    "GoPay" -> api.submitGoPayExpense(expense)
                    "Hana" ->  api.submitHanaExpense(expense)
                    "Mandiri" ->  api.submitMandiriExpense(expense)
                    "Octo" -> api.submitOctoExpense(expense)
                    "Ovo" -> api.submitOvoExpense(expense)
                    "Permata" ->  api.submitPermataExpense(expense)
                    "ShopeePay" -> api.submitShopeeExpense(expense)

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