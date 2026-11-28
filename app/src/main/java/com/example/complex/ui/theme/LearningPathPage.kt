
package com.example.complex.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PageBg = Color(0xFFF9FCFF)
private val MainBlue = Color(0xFF0798F7)
private val DarkBlue = Color(0xFF0074C7)

private enum class StepStatus {
    COMPLETED,
    LOCKED,
    CURRENT
}

private enum class StepIcon {
    BOOK,
    CHECK,
    REVIEW,
    STAR
}

private data class LearningStep(
    val title: String,
    val subtitle: String,
    val xp: String,
    val status: StepStatus,
    val icon: StepIcon
)

@Composable
fun LearningPathScreen(
    onTabSelected: (DashboardTab) -> Unit = {}
) {
    val steps = listOf(
        LearningStep(
            "Reading",
            "Completed.",
            "+ 30 XP",
            StepStatus.COMPLETED,
            StepIcon.BOOK
        ),
        LearningStep(
            "Word Practice",
            "Completed.",
            "+ 30 XP",
            StepStatus.COMPLETED,
            StepIcon.CHECK
        ),
        LearningStep(
            "Review",
            "Locked.",
            "+ 30 XP",
            StepStatus.LOCKED,
            StepIcon.REVIEW
        ),
        LearningStep(
            "Quiz",
            "Locked.",
            "+ 50 XP",
            StepStatus.CURRENT,
            StepIcon.STAR
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            DashboardHeader(
                hearts = 5,
                points = 1240
            )

            Spacer(Modifier.height(34.dp))

            Text(
                text = "Learning Path",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(Modifier.height(25.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(17.dp))
                    .background(MainBlue)
                    .padding(
                        horizontal = 25.dp,
                        vertical = 27.dp
                    )
            ) {
                Text(
                    text = "The Programming Process",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Step 3",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                Spacer(Modifier.height(22.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF75C5FF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((steps.size * 132).dp)
            ) {
                StepConnectors(
                    modifier = Modifier.matchParentSize(),
                    count = steps.size
                )

                Column {
                    steps.forEachIndexed { index, step ->
                        LearningStepRow(
                            step = step,
                            index = index,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(132.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(25.dp))
        }

        DashboardBottomBar(
            selectedTab = DashboardTab.LEARNING,
            onTabSelected = onTabSelected
        )
    }
}

@Composable
private fun StepConnectors(
    modifier: Modifier,
    count: Int
) {
    Canvas(modifier = modifier) {
        val rowHeight = 132.dp.toPx()
        val leftX = 86.dp.toPx()
        val rightX = size.width - 86.dp.toPx()

        for (i in 0 until count - 1) {
            val startX = if (i % 2 == 0) leftX else rightX
            val endX = if (i % 2 == 0) rightX else leftX

            val startY =
                i * rowHeight + rowHeight / 2 + 36.dp.toPx()

            val endY =
                (i + 1) * rowHeight + rowHeight / 2 - 36.dp.toPx()

            val connectorColor =
                if (i < 2) MainBlue else Color(0xFFA5A5A5)

            val path = Path().apply {
                moveTo(startX, startY)

                cubicTo(
                    startX,
                    startY + 55.dp.toPx(),
                    endX,
                    endY - 55.dp.toPx(),
                    endX,
                    endY
                )
            }

            drawPath(
                path = path,
                color = connectorColor,
                style = Stroke(
                    width = 4.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(
                            9.dp.toPx(),
                            8.dp.toPx()
                        )
                    )
                )
            )
        }
    }
}

@Composable
private fun LearningStepRow(
    step: LearningStep,
    index: Int,
    modifier: Modifier = Modifier
) {
    val isLeft = index % 2 == 0

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isLeft) {
            Arrangement.Start
        } else {
            Arrangement.End
        }
    ) {
        if (isLeft) {
            StepKey(step)
            Spacer(Modifier.width(12.dp))
            StepDescription(step)
        } else {
            StepDescription(
                step = step,
                alignEnd = true
            )
            Spacer(Modifier.width(12.dp))
            StepKey(step)
        }
    }
}

@Composable
private fun StepKey(step: LearningStep) {
    val faceColor = when (step.status) {
        StepStatus.COMPLETED -> MainBlue
        StepStatus.LOCKED -> Color(0xFFE4E5E6)
        StepStatus.CURRENT -> Color(0xFFFFE7A0)
    }

    val sideColor = when (step.status) {
        StepStatus.COMPLETED -> DarkBlue
        StepStatus.LOCKED -> Color(0xFF999999)
        StepStatus.CURRENT -> Color(0xFFFFD966)
    }

    val iconColor = when (step.status) {
        StepStatus.COMPLETED -> Color.White
        StepStatus.LOCKED -> Color(0xFF9FA2A4)
        StepStatus.CURRENT -> Color(0xFFFF8A00)
    }

    Box(
        modifier = Modifier
            .size(width = 72.dp, height = 74.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(sideColor)
            .padding(bottom = 5.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp))
                .background(faceColor),
            contentAlignment = Alignment.Center
        ) {
            StepSymbol(
                icon = step.icon,
                color = iconColor
            )
        }
    }
}

@Composable
private fun StepSymbol(
    icon: StepIcon,
    color: Color
) {
    Canvas(
        modifier = Modifier.size(43.dp)
    ) {
        val w = size.width
        val h = size.height

        val stroke = Stroke(
            width = 2.7.dp.toPx(),
            cap = StrokeCap.Round
        )

        when (icon) {
            StepIcon.BOOK -> {
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.2f)

                    cubicTo(
                        w * 0.3f, h * 0.08f,
                        w * 0.12f, h * 0.1f,
                        w * 0.1f, h * 0.22f
                    )

                    lineTo(w * 0.1f, h * 0.82f)

                    cubicTo(
                        w * 0.28f, h * 0.7f,
                        w * 0.4f, h * 0.78f,
                        w * 0.5f, h * 0.9f
                    )

                    moveTo(w * 0.5f, h * 0.2f)

                    cubicTo(
                        w * 0.7f, h * 0.08f,
                        w * 0.88f, h * 0.1f,
                        w * 0.9f, h * 0.22f
                    )

                    lineTo(w * 0.9f, h * 0.82f)

                    cubicTo(
                        w * 0.72f, h * 0.7f,
                        w * 0.6f, h * 0.78f,
                        w * 0.5f, h * 0.9f
                    )
                }

                drawPath(path, color, style = stroke)

                drawLine(
                    color = color,
                    start = Offset(w * 0.5f, h * 0.2f),
                    end = Offset(w * 0.5f, h * 0.9f),
                    strokeWidth = 2.5.dp.toPx()
                )
            }

            StepIcon.CHECK -> {
                val path = Path().apply {
                    moveTo(w * 0.18f, h * 0.52f)
                    lineTo(w * 0.4f, h * 0.73f)
                    lineTo(w * 0.84f, h * 0.25f)
                }

                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(
                        width = 3.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }

            StepIcon.REVIEW -> {
                drawArc(
                    color = color,
                    startAngle = 45f,
                    sweepAngle = 285f,
                    useCenter = false,
                    topLeft = Offset(w * 0.13f, h * 0.13f),
                    size = androidx.compose.ui.geometry.Size(
                        w * 0.74f,
                        h * 0.74f
                    ),
                    style = stroke
                )

                val arrow = Path().apply {
                    moveTo(w * 0.12f, h * 0.18f)
                    lineTo(w * 0.12f, h * 0.42f)
                    lineTo(w * 0.34f, h * 0.34f)
                }

                drawPath(
                    path = arrow,
                    color = color,
                    style = stroke
                )
            }

            StepIcon.STAR -> {
                val path = Path()

                for (i in 0 until 10) {
                    val angle =
                        Math.PI * i / 5.0 - Math.PI / 2

                    val radius =
                        if (i % 2 == 0) 0.46f else 0.21f

                    val x = w / 2 +
                            (kotlin.math.cos(angle) * w * radius).toFloat()

                    val y = h / 2 +
                            (kotlin.math.sin(angle) * h * radius).toFloat()

                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }

                path.close()

                drawPath(
                    path = path,
                    color = color,
                    style = stroke
                )
            }
        }
    }
}

@Composable
private fun StepDescription(
    step: LearningStep,
    alignEnd: Boolean = false
) {
    Column(
        modifier = Modifier.widthIn(max = 150.dp),
        horizontalAlignment = if (alignEnd) {
            Alignment.End
        } else {
            Alignment.Start
        }
    ) {
        Text(
            text = step.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = if (alignEnd) {
                TextAlign.End
            } else {
                TextAlign.Start
            }
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = "${step.subtitle}  ${step.xp}",
            fontSize = 12.sp,
            color = Color(0xFF426B8A),
            textAlign = if (alignEnd) {
                TextAlign.End
            } else {
                TextAlign.Start
            }
        )
    }
}
