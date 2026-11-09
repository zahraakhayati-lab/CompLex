package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BrandBlue = Color(0xFF0099FF)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF4A6075)
private val CardColor = Color(0xFFF0F0F0)
private val Violet = Color(0xFF6C63F0)

/**
 * illustration را می‌توانید با painterResource(R.drawable.xxx) بدهید.
 * اگر null باشد یک جایگزین ساده نمایش داده می‌شود.
 */
@Composable
fun AllSetScreen(
    userLevel: UserLevel?,
    englishLevel: EnglishLevel?,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    illustration: Painter? = null
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(16.dp))

            // --- Illustration ---
            if (illustration != null) {
                Image(
                    painter = illustration,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(220.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Violet.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Flag,
                        contentDescription = null,
                        tint = Violet,
                        modifier = Modifier.size(90.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Title ---
            Text(
                text = "You are all set!",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Your personalized learning path is ready.",
                fontSize = 14.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Your path card ---
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardColor,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                    Text(
                        text = "Your path",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleColor
                    )
                    if (userLevel != null) {
                        Spacer(modifier = Modifier.height(18.dp))
                        PathRow(icon = userLevel.icon, text = userLevel.title)
                    }
                    if (englishLevel != null) {
                        Spacer(modifier = Modifier.height(18.dp))
                        PathRow(icon = englishLevel.icon, text = englishLevel.title)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Continue ---
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
            ) {
                Text("Continue", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "You can always change your preferences\nin settings.",
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PathRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SubtitleColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 12.sp, color = SubtitleColor)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AllSetScreenPreview() {
    MaterialTheme {
        AllSetScreen(
            userLevel = UserLevel.UNIVERSITY,
            englishLevel = EnglishLevel.INTERMEDIATE,
            onBack = {},
            onContinue = {}
        )
    }
}