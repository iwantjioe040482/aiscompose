package com.arcadia.aiscompose.View

//import android.graphics.Color
import androidx.compose.ui.graphics.Color
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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.painterResource
import com.arcadia.aiscompose.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.focusTarget
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit,
                onRegisterClick: () -> Unit // ← Tambahkan ini
) {
    val isDarkTheme = isSystemInDarkTheme()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val emailFocusRequester = remember { FocusRequester() }

    val viewModel: LoginViewModel = viewModel()
    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()

    val legendColor = if (isDarkTheme) Color(0xFFEEEEEE) else Color(0xFF222222)
    val legendsColor = if (isDarkTheme) Color(0xFF222222) else Color(0xFFEEEEEE)

    val imageRes = if (isDarkTheme) R.drawable.img else R.drawable.imgwhite

    LaunchedEffect(Unit) {
        emailFocusRequester.requestFocus()
    }

    LaunchedEffect(loginResult?.token) {
        val token = loginResult?.token
        if (!token.isNullOrBlank()) {
            viewModel.resetLoginState()
            onLoginSuccess(token)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .widthIn(max = 400.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(imageRes),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "Arcadia Information System",
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineSmall.copy(color = legendColor),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = legendsColor),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isError = false
                        },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
                        singleLine = true,
                        isError = isError,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        isError = isError
                    )

                    AnimatedVisibility(visible = isError) {
                        Text(
                            text = "Email atau password tidak boleh kosong",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                isError = true
                            } else {
                                viewModel.login(email, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Login")
                    }
                    TextButton(
                        onClick = onRegisterClick,
                        modifier = Modifier.align(Alignment.End) // atau Alignment.CenterHorizontally sesuai desain
                    ) {
                        Text("Belum punya akun? Daftar")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

//                    TextButton(
//                        onClick = onRegisterClick,
//                        modifier = Modifier.align(Alignment.End)
//                    ) {
//                        Text("Belum punya akun? Daftar")
//                    }

                    val errorMessage = viewModel.errorMessage.value
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}


//@Composable
////fun LoginScreen(onLogin: (String, String) -> Unit = { _, _ -> }) {
//fun LoginScreen(onLoginSuccess: (String) -> Unit) {
//    val isDarkTheme = isSystemInDarkTheme()
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
////    var email by remember { mutableStateOf("") }
////    var password by remember { mutableStateOf("") }
//    var isError by remember { mutableStateOf(false) }
//    val emailFocusRequester = remember { FocusRequester() }
//    val viewModel: LoginViewModel = viewModel()
////    val loginResult = viewModel.loginResult.value
//    //val loginResult = viewModel.loginResult.collectAsState().value
//    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()
////    LaunchedEffect(loginResult) {
////        if (loginResult != null) {
////            onLogin(email, password) // ini akan trigger navigasi dari luar
////        }
////    }
//    val legendColor = if (isDarkTheme) Color(0xFFEEEEEE) else Color(0xFF222222)
//    // Autofocus saat screen muncul
//    LaunchedEffect(Unit) {
//        emailFocusRequester.requestFocus()
//    }
//    LaunchedEffect(loginResult?.token) {
//        //viewModel.resetLoginState()
//        val token = loginResult?.token
//        if (!token.isNullOrBlank()) {
//            viewModel.resetLoginState() //bikin saat login ke landscpae ilang layatnya putik
//            onLoginSuccess(token) // ✅ aman karena sudah dicek null dan blank
//        }
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 48.dp)
//                .widthIn(max = 480.dp)
//                .verticalScroll(rememberScrollState()), // ✅ tambahkan ini
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Image(
//                painter = painterResource(id = R.drawable.img),
//                contentDescription = "App Icon",
//                modifier = Modifier
//                    .size(120.dp)
//                    .padding(bottom = 16.dp)
//            )
//
//            Text(
//                text = "Arcadia Information System",
//                fontSize = 20.sp,
//                //color = legendColor,
//                style = MaterialTheme.typography.headlineMedium.copy(color = legendColor),
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 2.dp),
//                elevation = CardDefaults.cardElevation(8.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = {
//                            email = it
//                            isError = false
//                        },
//                        label = { Text("Username") },
//                        modifier =  Modifier
//                            .fillMaxWidth()
//                            .focusRequester(emailFocusRequester)
//                        ,
//                        singleLine = true,
//                        isError = isError,
//
//                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = {
//                            password = it
//                            isError = false
//                        },
//                        label = { Text("Password") },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true,
//                        visualTransformation = PasswordVisualTransformation(),
//                        keyboardOptions = KeyboardOptions.Default.copy(
//                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Password,
//                            imeAction = ImeAction.Done
//                        ),
//                        //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
//                        isError = isError
//                    )
//
//                    AnimatedVisibility(visible = isError) {
//                        Text(
//                            text = "Email atau password tidak boleh kosong",
//                            color = MaterialTheme.colorScheme.error,
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier.padding(top = 8.dp)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    val isLoading = loginResult?.token == null && loginResult?.message == null
//
//                    Button(
//                        onClick = {
//                            if (email.isBlank() || password.isBlank()) {
//                                isError = true
//                            } else {
//                                viewModel.login(email, password)
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                            .height(48.dp) // ✅ Tombol tinggi konsisten
//                        //enabled = !isLoading
//                    ) {
////                        if (!isLoading) {
////                            CircularProgressIndicator(
////                                modifier = Modifier.size(16.dp),
////                                color = Color.White,
////                                strokeWidth = 2.dp
////                            )
////                        } else {
//                            Text("Login")
////                        }
//                    }
//                    Spacer(modifier = Modifier.height(24.dp))
//                    val errorMessage = viewModel.errorMessage.value
//
//                    if (errorMessage != null) {
//                        Text(
//                            text = errorMessage,
//                            color = MaterialTheme.colorScheme.error,
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier.padding(top = 8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}