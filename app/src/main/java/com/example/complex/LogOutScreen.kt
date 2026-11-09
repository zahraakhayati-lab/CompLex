package com.example.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.complex.ui.theme.TextDark
import com.example.complex.ui.theme.TextGray
import com.example.complex.ui.theme.CompLexBlue

@Composable
fun LogoutScreen(
    onStayLoggedIn: () -> Unit = {},
    onLogoutConfirm: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F9FF))
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                    .clickable { onStayLoggedIn() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(Color(0xFFE3F2FD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = CompLexBlue,
                    modifier = Modifier.size(64.dp)
                )
            }
            Box(
                modifier = Modifier
                    .offset(x = 60.dp, y = (-50).dp)
                    .size(36.dp)
                    .background(Color.White, CircleShape)
                    .border(2.dp, CompLexBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("🔥", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Log out?", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your streak and XP are saved. You can\npick up right where you left off when\nyou log back in.",
            fontSize = 14.sp,
            color = Color(0xFF7A8C99),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CompLexBlue, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔥", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("12", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                }
                Text("Day streak", fontSize = 12.sp, color = TextGray)
            }

            Divider(modifier = Modifier.height(24.dp).width(1.dp), color = Color(0xFFE0E0E0))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Bolt, contentDescription = "XP", tint = Color(0xFF8B7CFF), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("1240", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                }
                Text("Total XP", fontSize = 12.sp, color = TextGray)
            }

            Divider(modifier = Modifier.height(24.dp).width(1.dp), color = Color(0xFFE0E0E0))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Level 3", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("Your level", fontSize = 12.sp, color = TextGray)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onStayLoggedIn() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CompLexBlue),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Stay logged in", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { onLogoutConfirm() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE53935))
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Log out", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutScreenPreview() {
    LogoutScreen()
}