package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue

@Composable
fun NameScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: (String) -> Unit = {}
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextDark
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Profile",
            tint = CompLexBlue,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "What's your name?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We'd love to know what to call you.",
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // فیلد نام (محدودیت 12 کاراکتر)
        Text(
            text = "First name *",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = firstName,
            onValueChange = {
                if (it.length <= 12) { // <-- 12 کاراکتر
                    firstName = it
                }
            },
            placeholder = { Text("e.g. Lily", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // فیلد نام خانوادگی (محدودیت 12 کاراکتر)
        Text(
            text = "Last name (Optional)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = {
                if (it.length <= 12) { // <-- 12 کاراکتر
                    lastName = it
                }
            },
            placeholder = { Text("e.g. Woods", color = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = BorderGray,
                focusedBorderColor = CompLexBlue,
                cursorColor = CompLexBlue
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val fullName = if (lastName.isBlank()) firstName else "$firstName $lastName"
                onContinueClick(fullName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = firstName.isNotBlank()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "→",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NameScreenPreview() {
    NameScreen()
}