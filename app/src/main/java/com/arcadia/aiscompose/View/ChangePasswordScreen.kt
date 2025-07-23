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


@Composable
fun ChangePasswordScreen(
    token:String,
    onChangePassword: (current: String, new: String, confirm: String) -> Unit,
    errorMessage: String? = null,
    successMessage: String? = null
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Change Password", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        if (errorMessage != null && showError) {
            Text(errorMessage, color = Color.Red)
        }

        if (successMessage != null) {
            Text(successMessage, color = Color.Green)
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
                    return@Button
                }
                onChangePassword(currentPassword, newPassword, confirmPassword)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }
    }
}