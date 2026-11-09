package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.CompLexBlue
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray

@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit = {},
    onCreateAccountClick: (String, String) -> Unit = { _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }

    // متغیرها برای نمایش پنجره‌های خطا
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
                    Text("Try Again", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Create your account", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Start learning Computer Engineering English.",
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // فیلد ایمیل
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

        // فیلد رمز عبور
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Password", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

        // فیلد تکرار رمز عبور
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm Password", color = TextGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Confirm Password", tint = TextGray, modifier = Modifier.size(20.dp))
            },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Confirm Password Visibility",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isTermsAccepted,
                onCheckedChange = { isTermsAccepted = it },
                colors = CheckboxDefaults.colors(checkedColor = CompLexBlue, uncheckedColor = BorderGray),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    append("I agree to the ")
                    withStyle(style = SpanStyle(color = CompLexBlue, fontWeight = FontWeight.SemiBold)) {
                        append("Terms & Privacy Policy")
                    }
                },
                fontSize = 14.sp,
                color = TextGray,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // دکمه ایجاد حساب
        Button(
            onClick = {
                // ۱. چک کردن فرمت ایمیل
                if (!email.contains("@") || !email.contains(".")) {
                    errorMessage = "Please enter a valid email address"
                    showErrorDialog = true
                }
                // ۲. چک کردن یکی بودن رمزها
                else if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    showErrorDialog = true
                }
                // ۳. چک کردن خالی نبودن رمز
                else if (password.isBlank()) {
                    errorMessage = "Password cannot be empty"
                    showErrorDialog = true
                }
                // ۴. اگه همه چی درست بود
                else {
                    onCreateAccountClick(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = isTermsAccepted && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            Text(text = "Create account", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account? ", fontSize = 14.sp, color = TextGray)
            Text(
                text = "Login",
                fontSize = 14.sp,
                color = CompLexBlue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen()
}