package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BgGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue



@Composable
fun AllSetScreen(
    selectedLevel: String,
    selectedEnglishLevel: String,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
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

        Spacer(modifier = Modifier.weight(1f))

        // تصویر تیک ۳بعدی (به جای عکس اصلی که تو پروژه نیست، از آیکون بزرگ استفاده میکنیم)
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = "Success",
            tint = Color(0xFF8B7CFF), // رنگ بنفش آبی شبیه عکس
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "You are all set!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your personalized learning path is ready.",
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // باکس اطلاعات (Your path)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgGray, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "Your path",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ردیف اول: سطح شغلی
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.School,
                    contentDescription = null,
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = selectedLevel,
                    fontSize = 15.sp,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ردیف دوم: سطح زبان
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.WorkspacePremium,
                    contentDescription = null,
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = selectedEnglishLevel,
                    fontSize = 15.sp,
                    color = TextGray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // دکمه Continue
        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CompLexBlue),
            shape = RoundedCornerShape(16.dp)
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

        Spacer(modifier = Modifier.height(16.dp))

        // متن راهنمای پایین
        Text(
            text = "You can always change your preferences\nin settings.",
            fontSize = 12.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AllSetScreenPreview() {
    AllSetScreen(
        selectedLevel = "University Student",
        selectedEnglishLevel = "Intermediate"
    )
}