package com.arcadia.aiscompose.View

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.aiscompose.Repository.TransactionViewModelFactory
import com.arcadia.aiscompose.ViewModel.ChangePasswordViewModel
import com.arcadia.aiscompose.ViewModel.TransactionViewModel
import androidx.compose.runtime.getValue

@Composable
fun ChangePasswordScreen(
    token:String,
    onChangePassword: (current: String, new: String, confirm: String) -> Unit,
//    errorMessage: String? = null,
//    successMessage: String? = null,
    onLogout: () -> Unit ,// ✅ untuk trigger logout dari parent composable
    viewModel: ChangePasswordViewModel = viewModel() // ✅ Tambahkan ini
) {



    var localErrorMessage by remember { mutableStateOf<String?>(null) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }

//    val errorMessage = viewModel.errorMessage.value
//    val successMessage = viewModel.successMessage.value
//    val shouldLogout = viewModel.shouldLogout.value
    val errorMessage by viewModel.errorMessage
    val successMessage by viewModel.successMessage
    val shouldLogout by viewModel.shouldLogout

    LaunchedEffect(Unit) {
        viewModel.setToken(token)
    }

    // 🔄 Pantau apakah sudah waktunya logout
    LaunchedEffect(shouldLogout) {
        if (shouldLogout) {
            onLogout()
        }
    }
    LaunchedEffect(successMessage) {
        if (!successMessage.isNullOrEmpty()) {
            currentPassword = ""
            newPassword = ""
            confirmPassword = ""
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Change Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Log.d("ChangePassword", "Error : ${errorMessage}")

        if (localErrorMessage != null && showError) {
            Text(localErrorMessage!!, color = Color.Red)
        }

        val message = successMessage
        if (message != null) {
            Text(message, color = Color.Green)
        }

        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                showError = true
                if (newPassword != confirmPassword) {
                    // Invalid confirm password
                    localErrorMessage = "Konfirmasi password tidak sama"
                    return@Button
                }
                localErrorMessage = null // reset kalau valid
                onChangePassword(currentPassword, newPassword, confirmPassword)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }
    }
}