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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue

data class Topic(val title: String, val icon: ImageVector)

@Composable
fun TopicSelectionScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    // استفاده از Set برای ذخیره گزینه‌های انتخاب شده (چون مولتیپل هست و هر کدوم فقط یکبار)
    var selectedTopics by remember { mutableStateOf(setOf<String>()) }

    val topics = listOf(
        Topic("Programming", Icons.Outlined.Code),
        Topic("Algorithms", Icons.Outlined.BarChart),
        Topic("AI & Machine Learning", Icons.Outlined.Memory),
        Topic("Data Structures", Icons.Outlined.ShowChart),
        Topic("Computer Networks", Icons.Outlined.Share),
        Topic("Operating Systems", Icons.Outlined.Settings),
        Topic("Databases", Icons.Outlined.Storage),
        Topic("Cybersecurity", Icons.Outlined.Security),
        Topic("Cloud Computing", Icons.Outlined.Cloud),
        Topic("Web Development", Icons.Outlined.Language),
        Topic("Software Engineering", Icons.Outlined.DeveloperBoard),
        Topic("Computer Architecture", Icons.Outlined.Memory) // آیکون مشابه
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // هدر: دکمه برگشت و نقطه‌های مرحله
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

            // نقطه‌های نشانگر مرحله (مرحله سوم آبیه)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(Color.LightGray, RoundedCornerShape(3.dp)))
                Box(modifier = Modifier.size(6.dp).background(Color.LightGray, RoundedCornerShape(3.dp)))
                Box(modifier = Modifier.width(32.dp).height(6.dp).background(CompLexBlue, RoundedCornerShape(3.dp)))
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(24.dp)) // برای تراز ماندن
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Which topic interests you?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose your English level.", // همون متنی که تو عکس بود
            fontSize = 16.sp,
            color = Color(0xFF4A7A9B)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // چیدمان دو ستونه با استفاده از Chunked
        val rows = topics.chunked(2)

        rows.forEach { rowTopics ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowTopics.forEach { topic ->
                    val isSelected = selectedTopics.contains(topic.title)

                    Row(
                        modifier = Modifier
                            .weight(1f) // تقسیم مساوی فضا بین دو ستون
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
                            .clickable {
                                selectedTopics = if (isSelected) {
                                    selectedTopics - topic.title
                                } else {
                                    selectedTopics + topic.title
                                }
                            }
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = topic.icon,
                            contentDescription = topic.title,
                            tint = if (isSelected) CompLexBlue else TextGray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = topic.title,
                            fontSize = 13.sp,
                            color = TextDark,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            maxLines = 1 // جلوگیری از شکستن خط در متن‌های طولانی
                        )
                    }
                }
                // اگر تعداد آیتم‌ها فرد بود، ستون خالی اضافه میکنیم تا چیدمان بهم نریزه
                if (rowTopics.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // دکمه ادامه
        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CompLexBlue,
                disabledContainerColor = CompLexBlue.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = selectedTopics.isNotEmpty() // حداقل یک موضوع باید انتخاب بشه
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
fun TopicSelectionScreenPreview() {
    TopicSelectionScreen()
}