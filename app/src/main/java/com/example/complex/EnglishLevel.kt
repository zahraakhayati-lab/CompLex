package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue
@Composable
fun EnglishLevelScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: (String) -> Unit = {} // حالا یه String می‌گیره
) {
    var selectedLevel by remember { mutableStateOf<String?>(null) }
    val levels = listOf("Beginner", "Intermediate", "advanced")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // هدر
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // نقطه‌های مرحله (مرحله دوم)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(Color.LightGray, RoundedCornerShape(3.dp)))
                Box(modifier = Modifier.width(32.dp).height(6.dp).background(CompLexBlue, RoundedCornerShape(3.dp)))
                Box(modifier = Modifier.size(6.dp).background(Color.LightGray, RoundedCornerShape(3.dp)))
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "How would you like to describe\nyour English?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose your English level.",
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // لیست سطح‌ها
        levels.forEach { level ->
            val isSelected = selectedLevel == level

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) CompLexBlue else BorderGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (isSelected) CompLexBlue.copy(alpha = 0.05f) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedLevel = level }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.WorkspacePremium,
                    contentDescription = level,
                    tint = if (isSelected) CompLexBlue else TextGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = level,
                    fontSize = 16.sp,
                    color = TextDark,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(40.dp))

        // دکمه ادامه
        Button(
            onClick = { onContinueClick(selectedLevel!!) }, // پاس دادن مقدار انتخاب شده
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = selectedLevel != null
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

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EnglishLevelScreenPreview() {
    EnglishLevelScreen()
}