package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lan
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BrandBlue = Color(0xFF0099FF)
private val TitleColor = Color(0xFF111111)
private val SubtitleColor = Color(0xFF4A6075)
private val BorderColor = Color(0xFF4A6075)
private val InactiveStep = Color(0xFFE6E6E6)

enum class InterestTopic(val title: String, val icon: ImageVector) {
    PROGRAMMING("Programming", Icons.Outlined.Code),
    ALGORITHMS("Algorithms", Icons.Outlined.BarChart),
    AI_ML("AI & Machine Learning", Icons.Outlined.Psychology),
    DATA_STRUCTURES("Data Structures", Icons.AutoMirrored.Outlined.ShowChart),
    NETWORKS("Computer Networks", Icons.Outlined.Lan),
    OPERATING_SYSTEMS("Operating Systems", Icons.Outlined.Settings),
    DATABASES("Databases", Icons.Outlined.Storage),
    CYBERSECURITY("Cybersecurity", Icons.Outlined.Shield),
    CLOUD("Cloud Computing", Icons.Outlined.Cloud),
    WEB("Web Development", Icons.Outlined.Language),
    SOFTWARE_ENGINEERING("Software Engineering", Icons.Outlined.Devices),
    ARCHITECTURE("Computer Architecture", Icons.Outlined.Memory)
}

@Composable
fun TopicInterestScreen(
    onBack: () -> Unit,
    onContinue: (List<InterestTopic>) -> Unit,
    currentStep: Int = 3,
    totalSteps: Int = 3
) {
    // نام انتخاب‌شده‌ها ذخیره می‌شود تا با چرخش صفحه از بین نرود
    var selectedNames by rememberSaveable { mutableStateOf(setOf<String>()) }

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
            TopicStepIndicator(
                currentStep = currentStep,
                totalSteps = totalSteps,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Title ---
            Text(
                text = "Which topic interests you?",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Choose your English level.",
                fontSize = 14.sp,
                color = SubtitleColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Topics grid (multi-select) ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(InterestTopic.values().toList()) { topic ->
                    val isSelected = topic.name in selectedNames
                    TopicChip(
                        topic = topic,
                        isSelected = isSelected,
                        onClick = {
                            selectedNames = if (isSelected) {
                                selectedNames - topic.name
                            } else {
                                selectedNames + topic.name
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Continue (حداقل یک موضوع باید انتخاب شود) ---
            Button(
                onClick = {
                    onContinue(InterestTopic.values().filter { it.name in selectedNames })
                },
                enabled = selectedNames.isNotEmpty(),
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
private fun TopicStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(totalSteps) { index ->
            val isCurrent = index == currentStep - 1
            Box(
                modifier = Modifier
                    .width(if (isCurrent) 40.dp else 20.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isCurrent) BrandBlue else InactiveStep)
            )
        }
    }
}

@Composable
private fun TopicChip(
    topic: InterestTopic,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
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
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = topic.icon,
                contentDescription = null,
                tint = if (isSelected) BrandBlue else SubtitleColor,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = topic.title,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected) BrandBlue else SubtitleColor,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TopicInterestScreenPreview() {
    MaterialTheme {
        TopicInterestScreen(onBack = {}, onContinue = {})
    }
}