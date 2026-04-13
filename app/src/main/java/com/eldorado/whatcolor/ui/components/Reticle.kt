package com.eldorado.whatcolor.ui.components

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eldorado.whatcolor.viewmodel.SamplePoint

private const val BRACKET_BOX_DP = 64f  // half-size of the bracket box in dp
private const val BRACKET_ARM_DP = 20f  // length of each bracket arm in dp
private const val STROKE_WIDTH_DP = 3f
private const val LABEL_WIDTH_DP = 160f

@Composable
fun ReticleOverlay(
    samplePoint: SamplePoint,
    colourName: String?,
    isFrozen: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    BoxWithConstraints(modifier = modifier) {
        val totalWidth = maxWidth
        val totalHeight = maxHeight

        val cx = totalWidth * samplePoint.x
        val cy = totalHeight * samplePoint.y

        // Canvas layer: pulsing ring + corner brackets
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cxPx = size.width * samplePoint.x
            val cyPx = size.height * samplePoint.y
            val halfBox = BRACKET_BOX_DP.dp.toPx()
            val armLen = BRACKET_ARM_DP.dp.toPx()
            val sw = STROKE_WIDTH_DP.dp.toPx()

            // Pulsing ring
            if (!isFrozen) {
                drawCircle(
                    color = Color.White.copy(alpha = pulseAlpha),
                    radius = halfBox * pulseScale,
                    center = Offset(cxPx, cyPx),
                    style = Stroke(width = sw)
                )
            }

            // Shadow pass (draw thick black lines slightly offset)
            val shadowOffset = 2f
            val shadowColor = Color.Black.copy(alpha = 0.8f)
            val shadowSw = sw + 4f

            drawBrackets(
                cxPx + shadowOffset, cyPx + shadowOffset,
                halfBox, armLen, shadowSw, shadowColor
            )
            // Main white brackets
            drawBrackets(cxPx, cyPx, halfBox, armLen, sw, Color.White)

            // Frozen indicator ring
            if (isFrozen) {
                drawCircle(
                    color = Color(0xFFFFD700),
                    radius = halfBox,
                    center = Offset(cxPx, cyPx),
                    style = Stroke(width = sw)
                )
            }
        }

        // Floating colour name label above the reticle
        if (colourName != null) {
            val labelY = cy - BRACKET_BOX_DP.dp - 32.dp
            Text(
                text = if (isFrozen) "$colourName  ❚❚" else colourName,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                ),
                modifier = Modifier
                    .width(LABEL_WIDTH_DP.dp)
                    .wrapContentHeight()
                    .align(Alignment.TopStart)
                    .offset(x = cx - (LABEL_WIDTH_DP / 2).dp, y = labelY)
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBrackets(
    cx: Float, cy: Float,
    halfBox: Float, armLen: Float,
    strokeWidth: Float, colour: Color
) {
    // Top-left
    drawLine(colour, Offset(cx - halfBox, cy - halfBox + armLen), Offset(cx - halfBox, cy - halfBox), strokeWidth)
    drawLine(colour, Offset(cx - halfBox, cy - halfBox), Offset(cx - halfBox + armLen, cy - halfBox), strokeWidth)
    // Top-right
    drawLine(colour, Offset(cx + halfBox, cy - halfBox + armLen), Offset(cx + halfBox, cy - halfBox), strokeWidth)
    drawLine(colour, Offset(cx + halfBox, cy - halfBox), Offset(cx + halfBox - armLen, cy - halfBox), strokeWidth)
    // Bottom-left
    drawLine(colour, Offset(cx - halfBox, cy + halfBox - armLen), Offset(cx - halfBox, cy + halfBox), strokeWidth)
    drawLine(colour, Offset(cx - halfBox, cy + halfBox), Offset(cx - halfBox + armLen, cy + halfBox), strokeWidth)
    // Bottom-right
    drawLine(colour, Offset(cx + halfBox, cy + halfBox - armLen), Offset(cx + halfBox, cy + halfBox), strokeWidth)
    drawLine(colour, Offset(cx + halfBox, cy + halfBox), Offset(cx + halfBox - armLen, cy + halfBox), strokeWidth)
}
