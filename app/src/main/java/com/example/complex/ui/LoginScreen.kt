package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
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
import androidx.lifecycle.viewmodel.compose.viewModel

private val BrandBlue = Color(0xFF0099FF)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF4A6075)
private val BorderColor = Color(0xFF4A6075)

/**
 * نسخه‌ای که به LoginViewModel وصل است (همان ViewModel قبلی شما).
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onBack: () -> Unit = {},
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPassword: () -> Unit = {},
    onTermsClick: () -> Unit = {}
) {
    LaunchedEffect(viewModel.isLoginSuccessful) {
        if (viewModel.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    LoginContent(
        isLoading = viewModel.isLoading,
        errorMessage = viewModel.errorMessage,
        onBack = onBack,
        onLogin = { email, password -> viewModel.loginUser(email, password) },
        onNavigateToRegister = onNavigateToRegister,
        onForgotPassword = onForgotPassword,
        onTermsClick = onTermsClick
    )
}

/**
 * بخش ظاهری صفحه (بدون وابستگی به ViewModel) تا بتوان Preview گرفت.
 */
@Composable
fun LoginContent(
    isLoading: Boolean,
    errorMessage: String?,
    onBack: () -> Unit,
    onLogin: (email: String, password: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onTermsClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var agreed by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // --- Back arrow ---
            IconButton(onClick = onBack, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Title ---
            Text(
                text = "Enter Your Account",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "See what you were learning & continue learning.",
                fontSize = 14.sp,
                color = SubtitleColor
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Email ---
            LoginPillTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = { Icon(Icons.Outlined.Email, null, tint = SubtitleColor) },
                keyboardType = KeyboardType.Email,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Password ---
            LoginPillTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = SubtitleColor) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = "Toggle password visibility",
                            tint = SubtitleColor
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                enabled = !isLoading
            )

            // --- Forget Password? ---
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Forget Password?",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = BrandBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = onForgotPassword)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Terms checkbox ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = agreed,
                    onCheckedChange = { agreed = it },
                    colors = CheckboxDefaults.colors(checkedColor = BrandBlue),
                    enabled = !isLoading
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = SubtitleColor)) { append("I agree to the ") }
                        withStyle(SpanStyle(color = BrandBlue, fontWeight = FontWeight.Medium)) {
                            append("Terms & Privacy Policy")
                        }
                    },
                    fontSize = 13.sp,
                    modifier = Modifier.clickable(onClick = onTermsClick)
                )
            }

            // --- Error ---
            val shownError = localError ?: errorMessage
            if (shownError != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = shownError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Login button ---
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        localError = "Please fill in all fields"
                    } else {
                        localError = null
                        onLogin(email.trim(), password)
                    }
                },
                enabled = !isLoading && agreed,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandBlue,
                    disabledContainerColor = BrandBlue.copy(alpha = 0.4f),
                    disabledContentColor = Color.White
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Register link ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account? ", fontSize = 12.sp, color = SubtitleColor)
                Text(
                    text = "Register",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandBlue,
                    modifier = Modifier.clickable(onClick = onNavigateToRegister)
                )
            }
        }
    }
}

@Composable
private fun LoginPillTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontSize = 12.sp, color = SubtitleColor) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(50),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = BorderColor,
            focusedBorderColor = BrandBlue,
            cursorColor = BrandBlue
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginContentPreview() {
    MaterialTheme {
        LoginContent(
            isLoading = false,
            errorMessage = null,
            onBack = {},
            onLogin = { _, _ -> },
            onNavigateToRegister = {},
            onForgotPassword = {},
            onTermsClick = {}
        )
    }
}
