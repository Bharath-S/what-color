package com.eldorado.whatcolor.viewmodel

import androidx.lifecycle.ViewModel
import com.eldorado.whatcolor.data.ColourResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SamplePoint(val x: Float, val y: Float)

class ColourViewModel : ViewModel() {

    private val _colourResult = MutableStateFlow<ColourResult?>(null)
    val colourResult: StateFlow<ColourResult?> = _colourResult.asStateFlow()

    private val _samplePoint = MutableStateFlow(SamplePoint(0.5f, 0.5f))
    val samplePoint: StateFlow<SamplePoint> = _samplePoint.asStateFlow()

    private val _isFrozen = MutableStateFlow(false)
    val isFrozen: StateFlow<Boolean> = _isFrozen.asStateFlow()

    fun updateColour(result: ColourResult) {
        _colourResult.value = result
    }

    fun setSamplePoint(x: Float, y: Float) {
        _samplePoint.value = SamplePoint(x.coerceIn(0f, 1f), y.coerceIn(0f, 1f))
    }

    fun freeze() {
        _isFrozen.value = true
    }

    fun unfreeze() {
        _isFrozen.value = false
    }
}
