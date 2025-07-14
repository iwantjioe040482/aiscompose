package com.arcadia.aiscompose.View
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.Model.EstWaterBill
import com.arcadia.aiscompose.ViewModel.WaterViewModel
import com.arcadia.aiscompose.Model.Water
import com.arcadia.aiscompose.Model.WaterView
import com.arcadia.aiscompose.Repository.WaterViewModelFactory


@Composable
fun WaterInputScreen(token: String) {

    val factory = remember { WaterViewModelFactory(token) }
    val viewModel: WaterViewModel = viewModel(factory = factory)

    LaunchedEffect(token) {
        viewModel.setToken(token)
        viewModel.fetchWater()
        viewModel.fetchWaterBill()
    }


    //val viewModel: WaterViewModel = viewModel()
    //val waterList by viewModel.waterList.collectAsState()
    val waterList : List<WaterView> = viewModel.waterList
    //val estwaterbillList by viewModel.balance.collectAsState()
    val estwaterbillList: List<Double> = viewModel.balance

    val today = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    var date by remember { mutableStateOf(today) }
    var value by remember { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLandscape = maxWidth > maxHeight

        if (isLandscape) {
            // 🔄 Layout untuk mode Landscape
            Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Text("Water List", style = MaterialTheme.typography.headlineSmall)
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Button(
//                        onClick = {
//                            viewModel.fetchWater()
//                            viewModel.fetchWaterBill()
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Refresh")
//                    }
//
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(waterList.size) { index ->
                            WaterItem(waterList[index])
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Est. Billing:", style = MaterialTheme.typography.bodyLarge)
//                        Text(
//                            "Rp ${"%,.2f".format(estwaterbillList)}",
//                            style = MaterialTheme.typography.titleMedium
//                        )
                        Text(
                            "Rp ${"%,.2f".format(estwaterbillList.firstOrNull() ?: 0.0)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date (yyyy-MM-dd)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = value,
                        onValueChange = { value = it },
                        label = { Text("Water Value") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.submitWater(Water(1, date, value.toDoubleOrNull() ?: 0.0))
                            value = ""
                            viewModel.fetchWater()
                            viewModel.fetchWaterBill()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text("Submit")
                    }
                }
            }
        } else {
            // 📱 Layout untuk mode Portrait
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(32.dp))

                Text("Water List", style = MaterialTheme.typography.headlineSmall)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(
//                    onClick = {
//                        viewModel.fetchWater()
//                        viewModel.fetchWaterBill()
//                    },
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .fillMaxWidth()
//                ) {
//                    Text("Refresh")
//                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
                    items(waterList.size) { index ->
                        WaterItem(waterList[index])
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Est. Billing:", style = MaterialTheme.typography.bodyLarge)
                    //Text("Rp ${"%,.2f".format(estwaterbillList)}", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Rp ${"%,.2f".format(estwaterbillList.firstOrNull() ?: 0.0)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (yyyy-MM-dd)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Water Value") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.submitWater(Water(1, date, value.toDoubleOrNull() ?: 0.0))
                        value = ""
                        viewModel.fetchWater()
                        viewModel.fetchWaterBill()
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }
        }
    }
}
