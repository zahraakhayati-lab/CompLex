
package com.example.complex.ui

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

@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onCreateAccount: (email: String, userId: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onTermsClick: () -> Unit = {},
    signUpViewModel: SignUpViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
    var agreed by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(signUpViewModel.isRegisterSuccessful) {
        if (signUpViewModel.isRegisterSuccessful) {
            val registeredEmail = signUpViewModel.registeredEmail
            val registeredId = signUpViewModel.registeredUserId

            signUpViewModel.resetRegisterState()

            if (registeredId.isNotBlank()) {
                onCreateAccount(registeredEmail, registeredId)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp),
                enabled = !signUpViewModel.isLoading
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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

            SignUpPillTextField(
                value = email,
                onValueChange = {
                    email = it
                    localError = null
                },
                placeholder = "Email",
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                        tint = SubtitleColor
                    )
                },
                keyboardType = KeyboardType.Email,
                enabled = !signUpViewModel.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignUpPillTextField(
                value = password,
                onValueChange = {
                    password = it
                    localError = null
                },
                placeholder = "Password",
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = SubtitleColor
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Outlined.VisibilityOff
                            } else {
                                Icons.Outlined.Visibility
                            },
                            contentDescription = "Toggle password visibility",
                            tint = SubtitleColor
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                enabled = !signUpViewModel.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignUpPillTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    localError = null
                },
                placeholder = "Confirm Password",
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = SubtitleColor
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { confirmVisible = !confirmVisible }
                    ) {
                        Icon(
                            imageVector = if (confirmVisible) {
                                Icons.Outlined.VisibilityOff
                            } else {
                                Icons.Outlined.Visibility
                            },
                            contentDescription = "Toggle password visibility",
                            tint = SubtitleColor
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (confirmVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                enabled = !signUpViewModel.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = agreed,
                    onCheckedChange = { agreed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = BrandBlue
                    ),
                    enabled = !signUpViewModel.isLoading
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(color = SubtitleColor)
                        ) {
                            append("I agree to the ")
                        }

                        withStyle(
                            SpanStyle(
                                color = BrandBlue,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Terms & Privacy Policy")
                        }
                    },
                    fontSize = 12.sp,
                    modifier = Modifier.clickable(
                        enabled = !signUpViewModel.isLoading,
                        onClick = onTermsClick
                    )
                )
            }

            val shownError =
                localError ?: signUpViewModel.errorMessage

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

            Button(
                onClick = {
                    localError = when {
                        email.isBlank() ->
                            "Please enter your email."

                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim())
                            .matches() ->
                            "Please enter a valid email address."

                        password.length < 6 ->
                            "Password must contain at least 6 characters."

                        confirmPassword.isBlank() ->
                            "Please confirm your password."

                        password != confirmPassword ->
                            "Passwords do not match."

                        !agreed ->
                            "Please accept the Terms & Privacy Policy."

                        else -> null
                    }

                    if (localError == null) {
                        signUpViewModel.registerUser(
                            email = email.trim(),
                            password = password
                        )
                    }
                },
                enabled = !signUpViewModel.isLoading && agreed,
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
                if (signUpViewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Create account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 12.sp,
                    color = SubtitleColor
                )

                Text(
                    text = "Login",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandBlue,
                    modifier = Modifier.clickable(
                        enabled = !signUpViewModel.isLoading,
                        onClick = onNavigateToLogin
                    )
                )
            }
        }
    }
}

@Composable
private fun SignUpPillTextField(
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
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 12.sp,
                color = SubtitleColor
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
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
