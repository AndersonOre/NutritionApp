package com.anderson.nutritionapp.presentation.user_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anderson.nutritionapp.presentation.user_auth.components.HeadingTextComponent
import com.anderson.nutritionapp.presentation.user_auth.components.MyTextFieldComponent
import com.anderson.nutritionapp.presentation.user_auth.components.NormalTextComponent
import com.anderson.nutritionapp.presentation.user_auth.components.PasswordTextFieldComponent
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLogin: suspend (String, String) -> String?, // returns error message or null
    onRegister: () -> Unit
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
                NormalTextComponent(value = "Hey, there")
                HeadingTextComponent(value = "Welcome Back")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column {
                MyTextFieldComponent(
                    labelValue = "Email",
                    icon = Icons.Outlined.Email,
                    value = email,
                    onValueChange = { email = it })
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(
                    labelValue = "Password",
                    icon = Icons.Outlined.Lock,
                    value = password,
                    onValueChange = { password = it })
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
                        } else {
                            coroutineScope.launch {
                                error = onLogin(trimmedEmail, password)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(16.dp))
                ClickableRegisterText(onRegister)

            }
        }
    }
}

@Composable
fun ClickableRegisterText(onRegister: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("Don't have an account? ")

        pushStringAnnotation(tag = "REGISTER", annotation = "register")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold
            )
        ) {
            append("Register")
        }
        pop()
    }

    ClickableText(
        text = annotatedText, style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center, fontSize = 16.sp
        ), modifier = Modifier.fillMaxWidth(), onClick = { offset ->
            annotatedText.getStringAnnotations("REGISTER", offset, offset).firstOrNull()
                ?.let { onRegister() }
        })
}