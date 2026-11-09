package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

private val BrandBlue = Color(0xFF0099FF)
private val CardBorder = Color(0xFFCFE6F5)

const val AVATAR_COUNT = 6

// رنگ جایگزین هر آواتار تا وقتی عکس‌ها اضافه نشده‌اند
private val PlaceholderColors = listOf(
    Color(0xFFB08AC0), Color(0xFFA37FB5), Color(0xFFB794C4),
    Color(0xFF9F7BB0), Color(0xFFAA86BA), Color(0xFFBE9CCB)
)

/**
 * آواتار گرد. اگر برای این index عکسی در avatarResIds باشد همان را نشان می‌دهد،
 * در غیر این صورت یک دایره‌ی بنفش با آیکن آدم.
 */
@Composable
fun AvatarCircle(
    index: Int,
    avatarResIds: List<Int>,
    modifier: Modifier = Modifier
) {
    val resId = avatarResIds.getOrNull(index)
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(PlaceholderColors[index % PlaceholderColors.size]),
        contentAlignment = Alignment.Center
    ) {
        if (resId != null) {
            Image(
                painter = painterResource(resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}

@Composable
fun AvatarPickerDialog(
    avatarResIds: List<Int> = emptyList(),
    currentAvatar: Int? = null,
    onDismiss: () -> Unit,
    onApply: (Int) -> Unit
) {
    var selected by remember { mutableStateOf(currentAvatar) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // دو ردیف سه‌تایی
                repeat(2) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { col ->
                            val index = row * 3 + col
                            AvatarItem(
                                index = index,
                                avatarResIds = avatarResIds,
                                isSelected = selected == index,
                                onClick = { selected = index }
                            )
                        }
                    }
                    if (row == 0) Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { selected?.let(onApply) },
                    enabled = selected != null,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandBlue,
                        disabledContainerColor = BrandBlue.copy(alpha = 0.4f),
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier
                        .width(125.dp)
                        .height(48.dp)
                ) {
                    Text("Apply", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun AvatarItem(
    index: Int,
    avatarResIds: List<Int>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(74.dp)
            .clip(CircleShape)
            .then(
                if (isSelected) Modifier.border(3.dp, BrandBlue, CircleShape) else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AvatarCircle(
            index = index,
            avatarResIds = avatarResIds,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarPickerDialogPreview() {
    MaterialTheme {
        AvatarPickerDialog(currentAvatar = 1, onDismiss = {}, onApply = {})
    }
}