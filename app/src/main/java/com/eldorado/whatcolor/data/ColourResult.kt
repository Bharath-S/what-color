package com.eldorado.whatcolor.data

data class ColourResult(
    val name: String,
    val description: String,
    val hex: String,
    val r: Int,
    val g: Int,
    val b: Int,
    val h: Float,
    val s: Float,
    val l: Float,
    val confidence: Float  // 0.0 to 1.0
)
