package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BrandBlue = Color(0xFF0099FF)
private val PageBg = Color(0xFFF4FAFF)
private val TitleColor = Color(0xFF111111)
private val TextBlue = Color(0xFF3E6079)
private val SubtitleColor = Color(0xFF4A6075)
private val NoteGray = Color(0xFF8A8F94)
private val CardBorder = Color(0xFFCFE6F5)
private val TrackGray = Color(0xFFDEDEDE)
private val FlameOrange = Color(0xFFFF8A00)

enum class DashboardDayState { DONE, TODAY, FUTURE }

enum class DashboardTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Outlined.Home),
    LEARNING("Learning", Icons.Outlined.Code),
    DICTIONARY("Dictionary", Icons.AutoMirrored.Outlined.MenuBook),
    COMPETITION("Competition", Icons.Outlined.EmojiEvents)
}

@Composable
fun HomeDashboardScreen(
    hearts: Int = 5,
    points: Int = 1240,
    level: Int = 3,
    levelProgress: Float = 0.8f,
    wordTitle: String = "Encryption",
    wordDefinition: String = "Definition : The process of converting data into a secure format.",
    wordExample: String = "“Encryption protects sensitive information from unauthorized access.”",
    streakDays: Int = 12,
    weekStates: List<DashboardDayState> = listOf(
        DashboardDayState.DONE, DashboardDayState.DONE, DashboardDayState.DONE,
        DashboardDayState.DONE, DashboardDayState.DONE,
        DashboardDayState.TODAY, DashboardDayState.FUTURE
    ),
    recentTitle: String = "The Programming Process",
    recentStep: String = "Step 3",
    recentProgress: Float = 0.75f,
    onMenuClick: () -> Unit = {},
    onTabSelected: (DashboardTab) -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(DashboardTab.HOME) }

    Surface(modifier = Modifier.fillMaxSize(), color = PageBg) {
        Box(modifier = Modifier.fillMaxSize()) {

            // ---------- محتوای اسکرول‌شونده ----------
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 120.dp)
            ) {
                // --- Top bar ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onMenuClick, modifier = Modifier.size(40.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.GridView,
                            contentDescription = "Menu",
                            tint = Color(0xFF8FA0AE),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    StatChip(Icons.Filled.Favorite, Color(0xFFE53935), hearts.toString())
                    Spacer(modifier = Modifier.width(12.dp))
                    StatChip(Icons.Filled.Bolt, Color(0xFF7C5CFA), points.toString())
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Your Progress ---
                Text(
                    text = "Your Progress",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // کارت سطح
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, CardBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(172.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your Level",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextBlue
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    progress = { levelProgress },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(60f),
                                    color = BrandBlue,
                                    trackColor = TrackGray,
                                    strokeWidth = 6.dp,
                                    strokeCap = StrokeCap.Round
                                )
                                Text(
                                    text = level.toString(),
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextBlue
                                )
                            }
                        }
                    }

                    // کارت لغت روز
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, CardBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(172.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = wordTitle,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextBlue
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = wordDefinition,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextBlue
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = wordExample,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                color = NoteGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // --- Streak ---
                Text(
                    text = "Streak",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BrandBlue,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White.copy(alpha = 0.22f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = FlameOrange,
                                    modifier = Modifier.size(52.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = streakDays.toString(),
                                    fontSize = 64.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    lineHeight = 70.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "days",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // روزهای هفته
                        val letters = listOf("M", "T", "W", "T", "F", "S", "S")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            letters.forEachIndexed { index, letter ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = letter,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    DayCircle(weekStates.getOrElse(index) { DashboardDayState.FUTURE })
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // --- Recent Reading ---
                Text(
                    text = "Recent Reading",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, CardBorder),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                        Text(
                            text = recentTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextBlue
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = recentStep, fontSize = 12.sp, color = NoteGray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(TrackGray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(recentProgress.coerceIn(0f, 1f))
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50))
                                    .background(BrandBlue)
                            )
                        }
                    }
                }
            }

            // ---------- Bottom navigation ----------
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                border = BorderStroke(1.dp, CardBorder),
                shadowElevation = 4.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DashboardTab.values().forEach { tab ->
                        val selected = tab == selectedTab
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedTab = tab
                                    onTabSelected(tab)
                                }
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                tint = if (selected) BrandBlue else Color(0xFF8FA0AE),
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = tab.label,
                                fontSize = 10.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selected) BrandBlue else Color(0xFF8FA0AE),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatChip(icon: ImageVector, tint: Color, text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color.White,
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TitleColor)
        }
    }
}

@Composable
private fun DayCircle(state: DashboardDayState) {
    when (state) {
        DashboardDayState.DONE -> Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = BrandBlue,
                modifier = Modifier.size(20.dp)
            )
        }

        DashboardDayState.TODAY -> Canvas(modifier = Modifier.size(34.dp)) {
            drawCircle(
                color = Color.White,
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                )
            )
        }

        DashboardDayState.FUTURE -> Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.3f))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDashboardScreenPreview() {
    MaterialTheme {
        HomeDashboardScreen()
    }
}