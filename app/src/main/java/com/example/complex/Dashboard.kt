package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
private val NoteGray = Color(0xFF8A8F94)
private val CardBorder = Color(0xFFCFE6F5)
private val SoftBlue = Color(0xFFDDF0FE)
private val LogoutRed = Color(0xFFE02020)

@Composable
fun ProfileDashboardScreen(
    fullName: String,
    email: String,
    level: Int = 3,
    userLevelTitle: String? = null,
    hearts: Int = 5,
    points: Int = 1240,
    selectedAvatar: Int? = null,
    avatarResIds: List<Int> = emptyList(),
    onAvatarSelected: (Int) -> Unit = {},
    onBack: () -> Unit,
    onFullNameClick: () -> Unit = {},
    onPasswordClick: () -> Unit = {},
    onLogOut: () -> Unit
) {
    var showAvatarPicker by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // --- Top bar ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onBack,
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF8FA0AE),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                ProfileStatChip(Icons.Filled.Favorite, Color(0xFFE53935), hearts.toString())
                Spacer(modifier = Modifier.width(12.dp))
                ProfileStatChip(Icons.Filled.Bolt, Color(0xFF7C5CFA), points.toString())
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Dashboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Profile card ---
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar + edit badge
                    Box(modifier = Modifier.size(80.dp)) {
                        if (selectedAvatar != null) {
                            AvatarCircle(
                                index = selectedAvatar,
                                avatarResIds = avatarResIds,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(SoftBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = null,
                                    tint = BrandBlue,
                                    modifier = Modifier.size(44.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(BrandBlue)
                                .border(2.dp, Color.White, CircleShape)
                                .clickable { showAvatarPicker = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit photo",
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = fullName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleColor
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = email, fontSize = 12.sp, color = SubtitleColor)

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        InfoPill("Level $level")
                        if (userLevelTitle != null) InfoPill(userLevelTitle)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Account ---
            Text(
                text = "Account",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                border = BorderStroke(1.dp, CardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    AccountRow(
                        icon = Icons.Outlined.Person,
                        title = "Full Name",
                        value = fullName,
                        onClick = onFullNameClick
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 14.dp),
                        color = Color(0xFFE6E6E6)
                    )
                    AccountRow(
                        icon = Icons.Outlined.Lock,
                        title = "Password",
                        value = "********",
                        onClick = onPasswordClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Log Out ---
            OutlinedButton(
                onClick = onLogOut,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color(0xFF7A7A7A)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = LogoutRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Log Out", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }

    // --- پنجره‌ی انتخاب آواتار (بقیه‌ی صفحه خودکار تیره می‌شود) ---
    if (showAvatarPicker) {
        AvatarPickerDialog(
            avatarResIds = avatarResIds,
            currentAvatar = selectedAvatar,
            onDismiss = { showAvatarPicker = false },
            onApply = { index ->
                onAvatarSelected(index)
                showAvatarPicker = false
            }
        )
    }
}

@Composable
private fun ProfileStatChip(icon: ImageVector, tint: Color, text: String) {
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
private fun InfoPill(text: String) {
    Surface(shape = RoundedCornerShape(50), color = SoftBlue) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = BrandBlue,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun AccountRow(
    icon: ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BrandBlue,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TitleColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 12.sp, color = NoteGray)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = BrandBlue
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileDashboardScreenPreview() {
    MaterialTheme {
        ProfileDashboardScreen(
            fullName = "Lily Woods",
            email = "Lily@test.com",
            userLevelTitle = "University student",
            onBack = {},
            onLogOut = {}
        )
    }
}