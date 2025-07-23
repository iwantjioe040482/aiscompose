package com.arcadia.aiscompose.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.arcadia.aiscompose.ui.theme.ArcadiaTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState

class MainActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MaterialTheme {
//                LoginScreen { username, _ ->
//                    navController.navigate("main") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
//
////                //MainScreen()
////                LoginScreen { email, password ->
////                //    println("Logging in with: $email / $password")
////                    // navigasi atau API call bisa di sini
////                }
//            }
            ArcadiaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by rememberSaveable { mutableStateOf("login")}
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }
    //var token by rememberSaveable { mutableStateOf<String?>(null) }
    //var token = remember { mutableStateOf<String?>(null) }
    val token = rememberSaveable { mutableStateOf<String?>(null) }
    //var isLoggedIn by remember { mutableStateOf(false) }
//    val navController = rememberNavController()
    when (currentScreen) {
        "login" -> {
            LoginScreen(
                onLoginSuccess = { newToken ->
                    token.value = newToken
                    currentScreen = "main"
                },
                onRegisterClick = {
                    currentScreen = "register"
                }
            )
        }

        "register" -> {
            RegisterScreen(
                onRegisterSuccess = {
                    currentScreen = "login"
                }
            )
        }

        "main" -> {
            MainScreen(
                tokenState = token,
                onLogout = {
                    token.value = null
                    currentScreen = "login"
                }
            )
        }
    }
//    if (!isLoggedIn) {
//        LoginScreen(
//            onLoginSuccess = {newToken ->
//                token.value = newToken
//                isLoggedIn = true // ✅ update login status
//            },
//            onRegisterClick = {
//                // Navigasi ke halaman register
//                //RegisterScreen(onRegisterSuccess = ) { }
//            }
//        )
//    } else {
//        MainScreen(
//            tokenState = token, // kirim token yang tersimpan
//            onLogout = {
//                token.value = null     // hapus token setelah logout
//                isLoggedIn = false
//            }
////            onLogout = {
////                token = token, // ⬅️ kirim ke MainScreen
////                isLoggedIn = false // ✅ logout cleanly
////            }
//        )
//    }
//    NavHost(navController = navController, startDestination = "login") {
//        composable("login") {
//            LoginScreen(
//                onLogin = { _, _ ->
//                    navController.navigate("main") {
//                        popUpTo("login") { inclusive = true } // Hapus screen login dari backstack
//                    }
//                }
//            )
//        }
//        composable("main") {
//            MainScreen()
//        }
//    }
}