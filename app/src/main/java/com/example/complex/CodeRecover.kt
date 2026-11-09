package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.CompLexBlue
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray

@Composable
fun RecoverCodeScreen(
    onBackClick: () -> Unit = {},
    onVerifyClick: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

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
                Text(text = "Invalid code", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
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
        IconButton(onClick = onBackClick, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Recover Your Password", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = buildAnnotatedString {
                append("Enter the ")
                withStyle(style = SpanStyle(color = CompLexBlue, fontWeight = FontWeight.Bold)) {
                    append("code")
                }
                append(" we sent you so we can move you to your account.")
            },
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = code,
            onValueChange = { if (it.length <= 4) code = it }, // محدودیت ۴ رقم
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // چک کردن کد (فرض میکنیم کد درست 1234 هست)
                if (code == "1234") {
                    onVerifyClick()
                } else {
                    showErrorDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = code.isNotBlank()
        ) {
            Text(text = "ok", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecoverCodeScreenPreview() {
    RecoverCodeScreen()
}