package com.arcadia.aiscompose.View

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.arcadia.aiscompose.ViewModel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
//fun LoginScreen(onLogin: (String, String) -> Unit = { _, _ -> }) {
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = viewModel()
//    val loginResult = viewModel.loginResult.value
    //val loginResult = viewModel.loginResult.collectAsState().value
    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()
//    LaunchedEffect(loginResult) {
//        if (loginResult != null) {
//            onLogin(email, password) // ini akan trigger navigasi dari luar
//        }
//    }
    LaunchedEffect(loginResult?.token) {
        viewModel.resetLoginState()
        val token = loginResult?.token
        if (!token.isNullOrBlank()) {
            viewModel.resetLoginState()
            onLoginSuccess(token) // ✅ aman karena sudah dicek null dan blank
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isError = false
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            isError = isError
        )

        if (isError) {
            Text(
                text = "Email atau password tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    isError = true
                } else {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(24.dp))
        val errorMessage = viewModel.errorMessage.value

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}