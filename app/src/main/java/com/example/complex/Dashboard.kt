package com.example.complex

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Favorite
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
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.BorderGray
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue


val avatarResources = listOf(
    R.drawable.avatar_1,
    R.drawable.avatar_2,
    R.drawable.avatar_3,
    R.drawable.avatar_4,
    R.drawable.avatar_5,
    R.drawable.avatar_6
)

@Composable
fun DashboardScreen(
    userName: String,
    userEmail: String,
    userPassword: String,
    profileIndex: Int,
    onProfileIndexChange: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    onEditNameClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {} // <-- پارامتر جدید
) {
    var showProfileDialog by remember { mutableStateOf(false) }
    var tempSelectedIndex by remember { mutableStateOf(profileIndex) }

    val displayName = userName.ifBlank { "User" }
    val maskedPassword = "*".repeat(userPassword.length)

    if (showProfileDialog) {
        AlertDialog(
            onDismissRequest = { showProfileDialog = false },
            text = {
                Column {
                    for (row in 0..1) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (col in 0..2) {
                                val index = row * 3 + col
                                if (index < avatarResources.size) {
                                    val isSelected = index == tempSelectedIndex
                                    Image(
                                        painter = painterResource(id = avatarResources[index]),
                                        contentDescription = "Avatar $index",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) CompLexBlue else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .clickable { tempSelectedIndex = index }
                                    )
                                }
                            }
                        }
                        if (row == 0) Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onProfileIndexChange(tempSelectedIndex)
                        showProfileDialog = false
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CompLexBlue)
                ) {
                    Text("Apply", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, BorderGray, CircleShape)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

        Spacer(modifier = Modifier.height(32.dp))

        Text("Dashboard", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD0E7FF), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = avatarResources[profileIndex]),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(CompLexBlue, CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .clickable {
                            tempSelectedIndex = profileIndex
                            showProfileDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit Profile",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(userEmail, fontSize = 14.sp, color = TextGray)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFD0E7FF), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Level 3", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CompLexBlue)
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFFD0E7FF), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("University student", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CompLexBlue)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Account", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFD0E7FF), RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEditNameClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Person, contentDescription = "Name", tint = CompLexBlue, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Full Name", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(displayName, fontSize = 13.sp, color = TextGray)
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow", tint = CompLexBlue)
            }

            HorizontalDivider(color = Color(0xFFD0E7FF), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onChangePasswordClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Lock, contentDescription = "Password", tint = CompLexBlue, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Password", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(maskedPassword, fontSize = 13.sp, color = TextGray)
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrow", tint = CompLexBlue)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = { onLogoutClick() }, // <-- رفتن به لاگ اوت
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE53935))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Log Out",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        userName = "Lily Woods",
        userEmail = "Lily@test.com",
        userPassword = "12345678",
        profileIndex = 0,
        onProfileIndexChange = {}
    )
}