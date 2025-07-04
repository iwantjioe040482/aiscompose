package com.arcadia.aiscompose.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class MainActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
                //LoginScreen { email, password ->
                //    println("Logging in with: $email / $password")
                    // navigasi atau API call bisa di sini
                //}
            }
        }
    }
}