package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue

@Composable
fun ChangePasswordScreen(
    currentPassword: String,
    onBackClick: () -> Unit = {},
    onSaveClick: (String) -> Unit = {}
) {
    var currentPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    var currentVisible by remember { mutableStateOf(false) }
    var newVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    // استیت‌های مربوط به پنجره‌ها
    var errorMessage by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // پنجره خطا
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            icon = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.Transparent, CircleShape)
                        .border(2.dp, Color(0xFFFFC107), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFC107))
                }
            },
            title = {
                Text(text = errorMessage, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CompLexBlue)
                ) {
                    Text("Ok", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // پنجره موفقیت (Successful)
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.Transparent, CircleShape)
                        .border(2.dp, Color(0xFF4CAF50), CircleShape), // رنگ سبز
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color(0xFF4CAF50), // رنگ سبز
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            title = {
                Text(text = "Successful", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onSaveClick(newPass) // <-- بعد از زدن Ok، رمز ذخیره میشه و برمیگرده داشبورد
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CompLexBlue)
                ) {
                    Text("Ok", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Change Password", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Update your security here.", fontSize = 16.sp, color = Color(0xFF4A7A9B))

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = currentPass,
            onValueChange = { if (it.length <= 30) currentPass = it },
            placeholder = { Text("Current Password", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Lock", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            trailingIcon = {
                IconButton(onClick = { currentVisible = !currentVisible }) {
                    Icon(
                        imageVector = if (currentVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            visualTransformation = if (currentVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPass,
            onValueChange = { if (it.length <= 30) newPass = it },
            placeholder = { Text("Password", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Lock", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            trailingIcon = {
                IconButton(onClick = { newVisible = !newVisible }) {
                    Icon(
                        imageVector = if (newVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            visualTransformation = if (newVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPass,
            onValueChange = { if (it.length <= 30) confirmPass = it },
            placeholder = { Text("Confirm Password", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Lock", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // چک کردن شرایط
                if (currentPass != currentPassword) {
                    errorMessage = "Current password is incorrect"
                    showErrorDialog = true
                } else if (newPass.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                    showErrorDialog = true
                } else if (newPass != confirmPass) {
                    errorMessage = "Passwords do not match"
                    showErrorDialog = true
                } else {
                    // همه چی درسته، پنجره موفقیت باز میشه
                    showSuccessDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = currentPass.isNotBlank() && newPass.isNotBlank() && confirmPass.isNotBlank()
        ) {
            Text(text = "Apply changes", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(currentPassword = "12345678")
}