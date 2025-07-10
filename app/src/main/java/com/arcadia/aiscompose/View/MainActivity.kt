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
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }
    var token by rememberSaveable { mutableStateOf<String?>(null) }
    //var isLoggedIn by remember { mutableStateOf(false) }
//    val navController = rememberNavController()
    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = {newToken ->
                token = newToken
                isLoggedIn = true // ✅ update login status
            }
        )
    } else {
        MainScreen(
            token = token ?: "", // kirim token yang tersimpan
            onLogout = {
                token = null     // hapus token setelah logout
                isLoggedIn = false
            }
//            onLogout = {
//                token = token, // ⬅️ kirim ke MainScreen
//                isLoggedIn = false // ✅ logout cleanly
//            }
        )
    }
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