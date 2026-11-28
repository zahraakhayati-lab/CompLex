
package com.example.complex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

private val BrandBlue = Color(0xFF0099FF)
private val PageBg = Color(0xFFF4FAFF)
private val TitleColor = Color(0xFF111111)
private val TextBlue = Color(0xFF3E6079)
private val NoteGray = Color(0xFF8A8F94)
private val CardBorder = Color(0xFFCFE6F5)
private val TrackGray = Color(0xFFDEDEDE)
private val FlameOrange = Color(0xFFFF8A00)

enum class DashboardDayState {
    DONE, TODAY, FUTURE
}

enum class DashboardTab(
    val label: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Outlined.Home),
    LEARNING("Learning", Icons.Outlined.Code),
    DICTIONARY("Dictionary", Icons.AutoMirrored.Outlined.MenuBook),
    COMPETITION("Competition", Icons.Outlined.EmojiEvents)
}

@Composable
fun HomeDashboardScreen(
    hearts: Int = 5,
    points: Int = 0,
    level: Int = 1,
    levelProgress: Float = 0f,
    wordTitle: String = "Encryption",
    wordDefinition: String = "Definition: The process of converting data into a secure format.",
    wordExample: String = "Encryption protects sensitive information from unauthorized access.",
    streakDays: Int = 0,
    weekStates: List<DashboardDayState> = List(7) {
        DashboardDayState.FUTURE
    },
    recentTitle: String = "Reading",
    recentStep: String = "Not started yet • 0 activities completed",
    recentProgress: Float = 0f,
    onMenuClick: () -> Unit = {},
    onTabSelected: (DashboardTab) -> Unit = {}
) {
    var selectedTab by rememberSaveable {
        mutableStateOf(DashboardTab.HOME)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PageBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 12.dp)
        ) {

            // هدر ثابت و مشترک
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                DashboardHeader(
                    hearts = hearts,
                    points = points,
                    onMenuClick = onMenuClick
                )
            }

            // محتوای Home قابل اسکرول
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Your Progress",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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

                            Spacer(Modifier.height(12.dp))

                            Box(
                                modifier = Modifier.size(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = {
                                        levelProgress.coerceIn(0f, 1f)
                                    },
                                    modifier = Modifier.fillMaxSize(),
                                    color = BrandBlue,
                                    trackColor = TrackGray,
                                    strokeWidth = 6.dp,
                                    strokeCap = StrokeCap.Round
                                )

                                Text(
                                    text = level.coerceAtLeast(1).toString(),
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextBlue
                                )
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, CardBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(172.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = wordTitle,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextBlue
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = wordDefinition,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextBlue
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = wordExample,
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                color = NoteGray
                            )
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "Streak",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )

                Spacer(Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BrandBlue,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 22.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Color.White.copy(alpha = 0.22f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = FlameOrange,
                                    modifier = Modifier.size(52.dp)
                                )
                            }

                            Spacer(Modifier.width(24.dp))

                            Row(
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = streakDays.coerceAtLeast(0).toString(),
                                    fontSize = 64.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    lineHeight = 70.sp
                                )

                                Spacer(Modifier.width(8.dp))

                                Text(
                                    text = "days",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        val letters = listOf(
                            "M", "T", "W", "T", "F", "S", "S"
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            letters.forEachIndexed { index, letter ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = letter,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    DayCircle(
                                        weekStates.getOrElse(index) {
                                            DashboardDayState.FUTURE
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                Text(
                    text = "Recent Reading",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TitleColor
                )

                Spacer(Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, CardBorder),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 24.dp
                        )
                    ) {
                        Text(
                            text = recentTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextBlue
                        )

                        Spacer(Modifier.height(2.dp))

                        Text(
                            text = recentStep,
                            fontSize = 12.sp,
                            color = NoteGray
                        )

                        Spacer(Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(TrackGray)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(
                                        recentProgress.coerceIn(0f, 1f)
                                    )
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(50))
                                    .background(BrandBlue)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(25.dp))
            }

            // نوار پایین ثابت و مشترک
            DashboardBottomBar(
                selectedTab = selectedTab,
                onTabSelected = {
                    selectedTab = it
                    onTabSelected(it)
                }
            )
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

        DashboardDayState.TODAY -> Canvas(
            modifier = Modifier.size(34.dp)
        ) {
            drawCircle(
                color = Color.White,
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(6f, 6f),
                        0f
                    )
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
