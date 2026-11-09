package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun EditNameScreen(
    currentName: String,
    onBackClick: () -> Unit = {},
    onSaveClick: (String) -> Unit = {}
) {
    // جدا کردن اسم و فامیل فعلی
    val nameParts = currentName.split(" ")
    var firstName by remember { mutableStateOf(nameParts.getOrNull(0) ?: "") }
    var lastName by remember { mutableStateOf(nameParts.getOrNull(1) ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // دکمه بازگشت
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

        Text(
            text = "What's your name?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You can change your name now.",
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
                if (it.length <= 12) firstName = it
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
                if (it.length <= 12) lastName = it
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

        // دکمه Apply Changes
        Button(
            onClick = {
                val fullName = if (lastName.isBlank()) firstName else "$firstName $lastName"
                onSaveClick(fullName)
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
            Text(
                text = "Apply Changes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditNameScreenPreview() {
    EditNameScreen(currentName = "Lily Woods")
}