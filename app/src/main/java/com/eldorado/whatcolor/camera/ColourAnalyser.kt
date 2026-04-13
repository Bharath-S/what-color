package com.eldorado.whatcolor.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.eldorado.whatcolor.data.ColourDatabase
import com.eldorado.whatcolor.data.ColourResult
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class ColourAnalyser(
    private val onColourDetected: (ColourResult) -> Unit,
    private val getSamplePoint: () -> Pair<Float, Float>
) : ImageAnalysis.Analyzer {

    private val sampleRadius = 10  // sample a 20x20 region

    override fun analyze(image: ImageProxy) {
        try {
            val (normX, normY) = getSamplePoint()
            val result = sampleColour(image, normX, normY)
            onColourDetected(result)
        } finally {
            image.close()
        }
    }

    private fun sampleColour(image: ImageProxy, normX: Float, normY: Float): ColourResult {
        val width = image.width
        val height = image.height
        val rotationDegrees = image.imageInfo.rotationDegrees

        // Map normalised screen coords to image coords accounting for sensor rotation
        val (imgNormX, imgNormY) = rotateNormalisedPoint(normX, normY, rotationDegrees)

        val centerX = (imgNormX * width).roundToInt().coerceIn(sampleRadius, width - sampleRadius - 1)
        val centerY = (imgNormY * height).roundToInt().coerceIn(sampleRadius, height - sampleRadius - 1)

        val yPlane = image.planes[0]
        val uPlane = image.planes[1]
        val vPlane = image.planes[2]

        val yRowStride = yPlane.rowStride
        val uvRowStride = uPlane.rowStride
        val uvPixelStride = uPlane.pixelStride

        val yBuffer = yPlane.buffer
        val uBuffer = uPlane.buffer
        val vBuffer = vPlane.buffer

        var totalY = 0L
        var totalU = 0L
        var totalV = 0L
        var count = 0

        for (dy in -sampleRadius until sampleRadius) {
            for (dx in -sampleRadius until sampleRadius) {
                val x = centerX + dx
                val y = centerY + dy
                if (x < 0 || y < 0 || x >= width || y >= height) continue

                val yIndex = y * yRowStride + x
                val uvIndex = (y / 2) * uvRowStride + (x / 2) * uvPixelStride

                if (yIndex < yBuffer.capacity() && uvIndex < uBuffer.capacity() && uvIndex < vBuffer.capacity()) {
                    totalY += yBuffer[yIndex].toInt() and 0xFF
                    totalU += uBuffer[uvIndex].toInt() and 0xFF
                    totalV += vBuffer[uvIndex].toInt() and 0xFF
                    count++
                }
            }
        }

        if (count == 0) {
            return ColourDatabase.findNearest(0f, 0f, 0.5f, 128, 128, 128)
        }

        val avgY = (totalY / count).toInt()
        val avgU = (totalU / count).toInt()
        val avgV = (totalV / count).toInt()

        val (r, g, b) = yuvToRgb(avgY, avgU, avgV)
        val (h, s, l) = rgbToHsl(r, g, b)

        return ColourDatabase.findNearest(h, s, l, r, g, b)
    }

    private fun rotateNormalisedPoint(x: Float, y: Float, rotationDegrees: Int): Pair<Float, Float> {
        return when (rotationDegrees) {
            90 -> Pair(y, 1f - x)
            180 -> Pair(1f - x, 1f - y)
            270 -> Pair(1f - y, x)
            else -> Pair(x, y)
        }
    }

    private fun yuvToRgb(y: Int, u: Int, v: Int): Triple<Int, Int, Int> {
        val yNorm = y - 16
        val uNorm = u - 128
        val vNorm = v - 128

        val r = (1.164f * yNorm + 1.596f * vNorm).roundToInt().coerceIn(0, 255)
        val g = (1.164f * yNorm - 0.392f * uNorm - 0.813f * vNorm).roundToInt().coerceIn(0, 255)
        val b = (1.164f * yNorm + 2.017f * uNorm).roundToInt().coerceIn(0, 255)

        return Triple(r, g, b)
    }

    private fun rgbToHsl(r: Int, g: Int, b: Int): Triple<Float, Float, Float> {
        val rf = r / 255f
        val gf = g / 255f
        val bf = b / 255f

        val maxC = max(rf, max(gf, bf))
        val minC = min(rf, min(gf, bf))
        val delta = maxC - minC

        val l = (maxC + minC) / 2f

        val s = if (delta < 0.001f) 0f else delta / (1f - abs(2f * l - 1f))

        val h = when {
            delta < 0.001f -> 0f
            maxC == rf -> 60f * (((gf - bf) / delta) % 6f)
            maxC == gf -> 60f * ((bf - rf) / delta + 2f)
            else -> 60f * ((rf - gf) / delta + 4f)
        }

        return Triple(((h % 360f) + 360f) % 360f, s.coerceIn(0f, 1f), l.coerceIn(0f, 1f))
    }
}
