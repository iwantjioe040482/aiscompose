package com.arcadia.aiscompose.View


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.setValue
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel
import com.arcadia.aiscompose.Model.Electricity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.ViewModel.AssetViewModel
import com.arcadia.aiscompose.Repository.AssetViewModelFactory
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ⬅️ WAJIB agar items() dikenali
import com.arcadia.aiscompose.Model.Assets
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AssetScreen(token: String) {
    Log.d("AssetViewModel", "Calling AssetSreen with token: $token")

    //val viewModel: AssetViewModel(token) = viewModel()
    val factory = remember { AssetViewModelFactory(token) }
    val viewModel: AssetViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        viewModel.setToken(token)
        viewModel.fetchAsset()
    }

    //val currentToken by viewModel.token.collectAsState()

    //val transactionList by viewModel.transactionList.collectAsState()

    //val transactionList  = viewModel.transactionList
    val transactionList: List<Assets> = viewModel.transactionList

    //val assets = viewModel.transactionList

    // Tampilan untuk portrait
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(32.dp))

        Text("Asset List", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

//        LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
//            items(transactionList.size) { index ->
//                AssetItem(transactionList[index])
//            }
//        }
        LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
            items(transactionList) { asset ->
                AssetItem(asset)
            }
        }

    }
}