package com.eldorado.whatcolor.ui.components

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eldorado.whatcolor.data.ColourResult
import java.util.Locale

@Composable
fun BottomPanel(
    colourResult: ColourResult?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var ttsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val t = TextToSpeech(context) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
        }
        tts = t
        onDispose {
            t.stop()
            t.shutdown()
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xDD111111),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            if (colourResult == null) {
                Text(
                    text = "Point camera at a colour",
                    color = Color(0xFF888888),
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(48.dp))
                return@Column
            }

            // Colour name row + speak button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Colour swatch
                val swatchColour = parseHexColour(colourResult.hex)
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(swatchColour)
                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                )

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = colourResult.name,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = colourResult.description,
                        color = Color(0xFFCCCCCC),
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Speak button
                IconButton(
                    onClick = {
                        if (ttsReady) {
                            tts?.language = Locale.getDefault()
                            tts?.speak(
                                "${colourResult.name}. ${colourResult.description}",
                                TextToSpeech.QUEUE_FLUSH,
                                null,
                                "colour_read"
                            )
                        }
                    },
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "Speak colour name",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Confidence bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Match",
                    color = Color(0xFF888888),
                    fontSize = 11.sp,
                    modifier = Modifier.width(40.dp)
                )
                LinearProgressIndicator(
                    progress = { colourResult.confidence },
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color.White,
                    trackColor = Color(0xFF444444),
                )
                Text(
                    text = "${(colourResult.confidence * 100).toInt()}%",
                    color = Color(0xFF888888),
                    fontSize = 11.sp,
                    modifier = Modifier.width(34.dp).padding(start = 6.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // Technical value chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TechChip(label = colourResult.hex.uppercase())
                TechChip(label = "RGB ${colourResult.r} ${colourResult.g} ${colourResult.b}")
                TechChip(label = "H${colourResult.h.toInt()} S${(colourResult.s * 100).toInt()} L${(colourResult.l * 100).toInt()}")
            }
        }
    }
}

@Composable
private fun TechChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF2A2A2A))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFAAAAAA),
            fontSize = 11.sp,
            maxLines = 1
        )
    }
}

private fun parseHexColour(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        Color.Gray
    }
}
