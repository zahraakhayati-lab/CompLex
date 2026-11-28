
package com.example.complex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DashboardBlue = Color(0xFF0798F7)
private val DashboardMuted = Color(0xFF8FA0AE)
private val DashboardBorder = Color(0xFFCFE6F5)

@Composable
fun DashboardHeader(
    hearts: Int,
    points: Int,
    onMenuClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.size(44.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.GridView,
                contentDescription = "Dashboard",
                tint = DashboardBlue,
                modifier = Modifier.size(27.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DashboardStatChip(
                icon = Icons.Filled.Favorite,
                value = hearts.toString(),
                iconColor = Color(0xFFFF5268)
            )

            DashboardStatChip(
                icon = Icons.Filled.Bolt,
                value = points.toString(),
                iconColor = Color(0xFFFFA000)
            )
        }
    }
}

@Composable
private fun DashboardStatChip(
    icon: ImageVector,
    value: String,
    iconColor: Color
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color.White,
        border = BorderStroke(1.dp, DashboardBorder)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(17.dp)
            )

            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF35536A)
            )
        }
    }
}

@Composable
fun DashboardBottomBar(
    selectedTab: DashboardTab,
    onTabSelected: (DashboardTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .navigationBarsPadding()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(1.dp, DashboardBorder),
        shadowElevation = 4.dp
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
                            onTabSelected(tab)
                        }
                        .padding(
                            horizontal = 8.dp,
                            vertical = 2.dp
                        )
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (selected) {
                            DashboardBlue
                        } else {
                            DashboardMuted
                        },
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (selected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        },
                        color = if (selected) {
                            DashboardBlue
                        } else {
                            DashboardMuted
                        }
                    )
                }
            }
        }
    }
}
