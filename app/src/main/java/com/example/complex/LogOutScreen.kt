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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BrandBlue = Color(0xFF0A8CFF)
private val BlueEdge = Color(0xFF0662C4)
private val PageBg = Color(0xFFF4FAFF)
private val TitleColor = Color(0xFF0E1B33)
private val SubtitleColor = Color(0xFF4A6075)
private val NoteGray = Color(0xFF5B6B7B)
private val CardBorder = Color(0xFFCFE6F5)
private val DividerColor = Color(0xFFE3E8EE)
private val FlameOrange = Color(0xFFFF8A00)
private val BoltViolet = Color(0xFF7C5CFA)
private val LogoutRed = Color(0xFFC62828)
private val PinkBorder = Color(0xFFF3B8BF)
private val SoftCircle = Color(0xFFDCEBFA)

@Composable
fun LogoutConfirmScreen(
    streakDays: Int = 12,
    totalXp: Int = 1240,
    level: Int = 3,
    onBack: () -> Unit,
    onStayLoggedIn: () -> Unit,
    onLogOut: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = PageBg) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 14.dp)
        ) {
            // --- Back ---
            Surface(
                onClick = onBack,
                shape = CircleShape,
                color = Color.White,
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TitleColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- Illustration ---
            LogoutIllustration(modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(28.dp))

            // --- Texts ---
            Text(
                text = "Log out?",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Your streak and XP are saved. You can pick up right where you left off when you log back in.",
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            // --- Stats card ---
            StatsCard(streakDays = streakDays, totalXp = totalXp, level = level)

            Spacer(modifier = Modifier.height(64.dp))

            // --- Buttons ---
            EdgeButton(
                container = BrandBlue,
                edge = BlueEdge,
                onClick = onStayLoggedIn
            ) {
                Text(
                    text = "Stay logged in",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            EdgeButton(
                container = Color.White,
                edge = PinkBorder,
                borderColor = PinkBorder,
                onClick = onLogOut
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    tint = LogoutRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Log out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LogoutRed
                )
            }
        }
    }
}

@Composable
private fun LogoutIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(width = 260.dp, height = 190.dp),
        contentAlignment = Alignment.Center
    ) {
        // دایره‌ی پس‌زمینه
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(SoftCircle)
        )

        // مربع آبی با لبه‌ی پایینی تیره
        Box(modifier = Modifier.rotate(6f)) {
            Box(
                modifier = Modifier
                    .offset(y = 7.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(BlueEdge)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.verticalGradient(listOf(Color(0xFF2B95FF), Color(0xFF0B58D0)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        // نشان آتش
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 26.dp)
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .border(2.dp, BrandBlue, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.LocalFireDepartment,
                contentDescription = null,
                tint = FlameOrange,
                modifier = Modifier.size(26.dp)
            )
        }

        // ستاره‌های تزئینی
        Sparkle(
            color = Color(0xFF1E88FF),
            dimension = 22.dp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 28.dp, start = 12.dp)
        )
        Sparkle(
            color = Color(0xFF8CC8FF),
            dimension = 14.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 18.dp, end = 22.dp)
        )
    }
}

@Composable
private fun Sparkle(color: Color, dimension: Dp, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(dimension)) {
        val w = size.width
        val c = w / 2f
        val path = Path().apply {
            moveTo(c, 0f)
            quadraticBezierTo(c, c, w, c)
            quadraticBezierTo(c, c, c, w)
            quadraticBezierTo(c, c, 0f, c)
            quadraticBezierTo(c, c, c, 0f)
            close()
        }
        drawPath(path, color)
    }
}

@Composable
private fun StatsCard(streakDays: Int, totalXp: Int, level: Int) {
    val shape = RoundedCornerShape(20.dp)
    // لایه‌ی بیرونی آبی = حاشیه + لبه‌ی ضخیم‌تر پایین
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(BrandBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 1.dp, end = 1.dp, top = 1.dp, bottom = 4.dp)
                .clip(RoundedCornerShape(19.dp))
                .background(Color.White)
                .padding(vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                modifier = Modifier.weight(1f),
                label = "Day streak"
            ) {
                Icon(Icons.Filled.LocalFireDepartment, null, tint = FlameOrange, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("$streakDays", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TitleColor)
            }
            VerticalDivider(height = 40.dp)
            StatItem(
                modifier = Modifier.weight(1f),
                label = "Total XP"
            ) {
                Icon(Icons.Filled.Bolt, null, tint = BoltViolet, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("$totalXp", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TitleColor)
            }
            VerticalDivider(height = 40.dp)
            StatItem(
                modifier = Modifier.weight(1f),
                label = "Your level"
            ) {
                Text("Level $level", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TitleColor)
            }
        }
    }
}

@Composable
private fun StatItem(
    modifier: Modifier,
    label: String,
    value: @Composable RowScope.() -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically, content = value)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, fontSize = 12.sp, color = NoteGray)
    }
}

@Composable
private fun VerticalDivider(height: Dp) {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(height)
            .background(DividerColor)
    )
}

/**
 * دکمه‌ی سه‌بعدی با لبه‌ی رنگی در پایین (مثل طرح).
 */
@Composable
private fun EdgeButton(
    container: Color,
    edge: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color? = null,
    content: @Composable RowScope.() -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(shape)
            .background(edge)
            .then(if (borderColor != null) Modifier.border(1.dp, borderColor, shape) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 4.dp)
                .clip(shape)
                .background(container)
                .clickable(onClick = onClick),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LogoutConfirmScreenPreview() {
    MaterialTheme {
        LogoutConfirmScreen(onBack = {}, onStayLoggedIn = {}, onLogOut = {})
    }
}