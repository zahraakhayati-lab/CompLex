package com.example.complex

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue

@Composable
fun HomeScreen(
    onNavigateToDashboard: () -> Unit = {}, // <-- پارامتر جدید
    onNavigateToLearning: () -> Unit = {},
    onNavigateToDictionary: () -> Unit = {},
    onNavigateToCompetition: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            // منوی پایین
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(Color(0xFFF5F9FF), RoundedCornerShape(24.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem("Home", Icons.Outlined.Home, true)
                BottomNavItem("Learning", Icons.Outlined.Code, false, onNavigateToLearning)
                BottomNavItem("Dictionary", Icons.Outlined.MenuBook, false, onNavigateToDictionary)
                BottomNavItem("Competition", Icons.Outlined.EmojiEvents, false, onNavigateToCompetition)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // هدر: آیکون منو و سکه و قلب
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.GridView,
                    contentDescription = "Menu",
                    tint = TextGray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onNavigateToDashboard() } // <-- رفتن به داشبورد
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // کارت قلب (با استروک)
                    Row(
                        modifier = Modifier
                            .border(1.dp, BorderGray, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Favorite, contentDescription = "Hearts", tint = Color.Red, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("5", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    // کارت رعد و برق (با استروک)
                    Row(
                        modifier = Modifier
                            .border(1.dp, BorderGray, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.Bolt, contentDescription = "Coins", tint = Color(0xFF8B7CFF), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("1240", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Your Progress", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)

            Spacer(modifier = Modifier.height(16.dp))

            // بخش پیشرفت (دو کارت کنار هم)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // کارت سطح (دایره)
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Your Level", fontSize = 12.sp, color = TextGray, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.size(80.dp)) {
                                drawArc(
                                    color = Color.LightGray.copy(alpha = 0.3f),
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                                )
                                drawArc(
                                    color = CompLexBlue,
                                    startAngle = -90f,
                                    sweepAngle = 270f,
                                    useCenter = false,
                                    style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            Text("3", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                    }
                }

                // کارت تعریف (Encryption) - رنگ‌ها اصلاح شدند
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Encryption",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A7A9B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Definition: The process of converting data into a secure format.",
                            fontSize = 10.sp,
                            color = Color(0xFF7A8C99),
                            lineHeight = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“Encryption protects sensitive information from unauthorized access.”",
                            fontSize = 10.sp,
                            color = Color(0xFF7A8C99),
                            fontStyle = FontStyle.Italic,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Streak", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)

            Spacer(modifier = Modifier.height(12.dp))

            // کارت استریک (آبی)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CompLexBlue)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF0077CC), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔥", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("12", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("days", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        val activeDays = 5

                        days.forEachIndexed { index, day ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(day, color = Color.White, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                if (index < activeDays) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(Color.White, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Outlined.Check, contentDescription = "Done", tint = CompLexBlue, modifier = Modifier.size(16.dp))
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .border(1.dp, Color.White, CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Recent Reading", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)

            Spacer(modifier = Modifier.height(12.dp))

            // کارت مطالعه اخیر
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "The Programming Process",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A7D9A)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Step 3",
                        fontSize = 14.sp,
                        color = Color(0xFFA0AAB2)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // نوار پیشرفت کامپوزیتی (خاکستری در پس‌زمینه، آبی در پیش‌زمینه)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFFF0F2F5), RoundedCornerShape(4.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .fillMaxHeight()
                                .background(CompLexBlue, RoundedCornerShape(4.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BottomNavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) CompLexBlue else TextGray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            color = if (isSelected) CompLexBlue else TextGray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}