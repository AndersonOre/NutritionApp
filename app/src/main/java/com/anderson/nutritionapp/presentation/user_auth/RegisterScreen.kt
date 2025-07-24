package com.anderson.nutritionapp.presentation.user_auth


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.anderson.nutritionapp.presentation.user_auth.components.HeadingTextComponent
import com.anderson.nutritionapp.presentation.user_auth.components.MyTextFieldComponent
import com.anderson.nutritionapp.presentation.user_auth.components.NormalTextComponent
import com.anderson.nutritionapp.presentation.user_auth.components.PasswordTextFieldComponent
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegister: suspend (String, String) -> String?, // returns error message or null
    onBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                NormalTextComponent(value = "Create your account")
                HeadingTextComponent(value = "Register")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = "Email",
                    icon = Icons.Outlined.Email,
                    value = email,
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Password",
                    icon = Icons.Outlined.Lock,
                    value = password,
                    onValueChange = { password = it }
                )
                if (error != null) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        error = null
                        val trimmedEmail = email.trim()
                        if (trimmedEmail.isBlank() || password.isBlank()) {
                            error = "Please enter both email and password"
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                            error = "Please enter a valid email address"
                        } else {
                            coroutineScope.launch {
                                error = onRegister(trimmedEmail, password)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login")
                }
            }
        }
    }
}