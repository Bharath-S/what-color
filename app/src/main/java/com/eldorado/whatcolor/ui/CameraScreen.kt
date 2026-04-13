package com.eldorado.whatcolor.ui

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.eldorado.whatcolor.camera.ColourAnalyser
import com.eldorado.whatcolor.ui.components.BottomPanel
import com.eldorado.whatcolor.ui.components.ReticleOverlay
import com.eldorado.whatcolor.viewmodel.ColourViewModel
import java.util.concurrent.Executors

@Composable
fun CameraScreen(viewModel: ColourViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val colourResult by viewModel.colourResult.collectAsState()
    val samplePoint by viewModel.samplePoint.collectAsState()
    val isFrozen by viewModel.isFrozen.collectAsState()

    val previewView = remember { PreviewView(context) }
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val analyser = ColourAnalyser(
                onColourDetected = { result ->
                    if (!viewModel.isFrozen.value) {
                        viewModel.updateColour(result)
                    }
                },
                getSamplePoint = {
                    val sp = viewModel.samplePoint.value
                    Pair(sp.x, sp.y)
                }
            )

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { it.setAnalyzer(analysisExecutor, analyser) }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalysis
                )
            } catch (e: Exception) {
                Log.e("CameraScreen", "Camera binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            analysisExecutor.shutdown()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val nx = offset.x / size.width
                        val ny = offset.y / size.height
                        viewModel.setSamplePoint(nx, ny)
                        viewModel.unfreeze()
                    },
                    onLongPress = {
                        viewModel.freeze()
                    }
                )
            }
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        ReticleOverlay(
            samplePoint = samplePoint,
            colourName = colourResult?.name,
            isFrozen = isFrozen,
            modifier = Modifier.fillMaxSize()
        )

        BottomPanel(
            colourResult = colourResult,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
