package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BrandBlue = Color(0xFF0099FF)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF4A6075)
private val BorderColor = Color(0xFF4A6075)
private val InactiveStep = Color(0xFFE6E6E6)

enum class UserLevel(val title: String, val icon: ImageVector) {
    HIGH_SCHOOL("High School Student", Icons.AutoMirrored.Outlined.MenuBook),
    UNIVERSITY("University Student", Icons.Outlined.School),
    GRADUATE("Graduate Student", Icons.Outlined.WorkspacePremium),
    DEVELOPER("Software Developer", Icons.Outlined.Devices),
    RESEARCHER("Researcher", Icons.Outlined.Search),
    PROFESSIONAL("Working Professional", Icons.Outlined.BusinessCenter),
    OTHER("Other", Icons.Outlined.MoreHoriz)
}

@Composable
fun LevelScreen(
    onBack: () -> Unit,
    onContinue: (UserLevel) -> Unit,
    currentStep: Int = 1,
    totalSteps: Int = 3
) {
    var selected by rememberSaveable { mutableStateOf<UserLevel?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
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

            Spacer(modifier = Modifier.height(24.dp))

            // --- Step indicator ---
            StepIndicator(
                currentStep = currentStep,
                totalSteps = totalSteps,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Title ---
            Text(
                text = "What’s your current level?",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "This helps us personalize your content.",
                fontSize = 14.sp,
                color = SubtitleColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Options (scrollable for small screens) ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                UserLevel.values().forEach { level ->
                    LevelOption(
                        level = level,
                        isSelected = level == selected,
                        onClick = { selected = level }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Continue ---
            Button(
                onClick = { selected?.let(onContinue) },
                enabled = selected != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandBlue,
                    disabledContainerColor = BrandBlue.copy(alpha = 0.4f),
                    disabledContentColor = Color.White
                )
            ) {
                Text("Continue", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
private fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(totalSteps) { index ->
            val active = index < currentStep
            Box(
                modifier = Modifier
                    .width(if (index == currentStep - 1) 40.dp else 20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (active) BrandBlue else InactiveStep)
            )
        }
    }
}

@Composable
private fun LevelOption(
    level: UserLevel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) BrandBlue.copy(alpha = 0.08f) else Color.White,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) BrandBlue else BorderColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = level.icon,
                contentDescription = null,
                tint = if (isSelected) BrandBlue else SubtitleColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = level.title,
                fontSize = 12.sp,
                color = if (isSelected) BrandBlue else SubtitleColor,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LevelScreenPreview() {
    MaterialTheme {
        LevelScreen(onBack = {}, onContinue = {})
    }
}
