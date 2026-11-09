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

private val BrandBlue = Color(0xFF0099FF)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF4A6075)
private val BorderColor = Color(0xFF4A6075)

@Composable
fun SignUpScreen(
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onBack: () -> Unit,
    onCreateAccount: (email: String, password: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onTermsClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
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
                text = "Create your account",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Start learning Computer Engineering English.",
                fontSize = 14.sp,
                color = SubtitleColor,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Email ---
            PillTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = { Icon(Icons.Outlined.Email, null, tint = SubtitleColor) },
                keyboardType = KeyboardType.Email,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Password ---
            PillTextField(
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

            Spacer(modifier = Modifier.height(16.dp))

            // --- Confirm Password ---
            PillTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = SubtitleColor) },
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(
                            imageVector = if (confirmVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = "Toggle password visibility",
                            tint = SubtitleColor
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    fontSize = 12.sp,
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

            // --- Create account button ---
            Button(
                onClick = {
                    localError = when {
                        email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                            "Please fill in all fields"
                        password != confirmPassword -> "Passwords do not match"
                        !agreed -> "You must accept the Terms & Privacy Policy"
                        else -> null
                    }
                    if (localError == null) onCreateAccount(email.trim(), password)
                },
                enabled = !isLoading && agreed,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
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
                    Text("Create account", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- Login link ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account? ", fontSize = 12.sp, color = SubtitleColor)
                Text(
                    text = "Login",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandBlue,
                    modifier = Modifier.clickable(onClick = onNavigateToLogin)
                )
            }
        }
    }
}

@Composable
private fun PillTextField(
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
private fun SignUpScreenPreview() {
    MaterialTheme {
        SignUpScreen(
            onBack = {},
            onCreateAccount = { _, _ -> },
            onNavigateToLogin = {}
        )
    }
}
