package com.anderson.nutritionapp.presentation.user_auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        fontSize = 24.sp,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        fontSize = 30.sp,
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        label = { Text(text = labelValue) },
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        label = { Text(text = labelValue) },
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        trailingIcon = {
            val iconImage = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = iconImage, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun BottomComponent(
    textQuery: String,
    textClickable: String,
    action: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = action)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = textQuery)
            TextButton(onClick = onClick) {
                Text(text = textClickable)
            }
        }
    }
}