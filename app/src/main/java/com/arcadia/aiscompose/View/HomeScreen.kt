package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arcadia.aiscompose.ViewModel.ElectricityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.ViewModel.WaterViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.remember
import com.arcadia.aiscompose.Model.Assets
import com.arcadia.aiscompose.Model.ElectricityView
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.WaterViewModelFactory
import com.arcadia.aiscompose.Repository.ElectricityViewModelFactory


@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
fun HomeScreen(token: String) {
    //val vm: ElectricityViewModel = viewModel()
   // val vm2: WaterViewModel=viewModel()

    //val factory = remember { WaterViewModelFactory(token) }
    val waterFactory = remember { WaterViewModelFactory(token) }
    val electricityFactory = remember { ElectricityViewModelFactory(token) }

    val viewModel: WaterViewModel = viewModel(factory = waterFactory)
    val viewModel2: ElectricityViewModel = viewModel(factory = electricityFactory)

    LaunchedEffect(token) {
        viewModel2.setToken(token)
        viewModel2.fetchElectricity()
        viewModel.setToken(token)
        viewModel.fetchWater()
        Log.d("WaterDebug", "HomeScreem: ${token} data")
    }

//    LaunchedEffect(Unit) {
//        vm.fetchElectricity()
//        //vm2.fetchWater()
//    }

    val landscape = isLandscape()

    if (landscape) {
        val scrollState = rememberScrollState()
        Row (modifier = Modifier.fillMaxSize()
            .horizontalScroll(scrollState)
            .padding(8.dp)
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(8.dp)) {
                Spacer(modifier = Modifier.height(32.dp))
                //val values by vm.electricityList.collectAsState()
                val values : List<ElectricityView> = viewModel2.electricityList

                if (values.isNotEmpty()) {
                    Column {
                        Text(
                            text = "Grafik Pemakaian Listrik",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                        //      ElectricityLineChart(data = values)
                        ElectricityLineChartView(data = values)
                    }
                } else {
                    Text("Memuat data...")
                }
                // Chart 1
            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(8.dp)) {
                Spacer(modifier = Modifier.height(32.dp))

                //val values2 = vm2.waterList.value
                //val values2 by vm2.waterList.collectAsState()
                val values2 : List<WaterView> = viewModel.waterList


                if (values2.isNotEmpty()) {
                    Column {
                        Text(
                            text = "Grafik Pemakaian Air",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                        WaterLineChartView(data = values2)
                        //WaterLineChart(data = values2)
                    }
                } else {
                    Text("Memuat data...")
                }

                // Chart 1
            }

        }
    } else {
            Column(modifier = Modifier
                .padding(1.dp)) {
                //val values by vm.electricityList.collectAsState()
                val values : List<ElectricityView> = viewModel2.electricityList

                if (values.isNotEmpty()) {
                    Column {
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(
                            text = "Grafik Pemakaian Listrik",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                        //      ElectricityLineChart(data = values)
                        ElectricityLineChartView(data = values)
                    }
                } else {
                    Text("Memuat data...")
                }
                // Chart 1

                //val values2 = vm2.waterList.value
                //val values2 by vm2.waterList.collectAsState()
                val values2 : List<WaterView> = viewModel.waterList

                if (values2.isNotEmpty()) {
                    Column {
                        Text(
                            text = "Grafik Pemakaian Air",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                        WaterLineChartView(data = values2)
                        //WaterLineChart(data = values2)
                    }
                } else {
                    Text("Memuat data...")
                }

            }


        }



}