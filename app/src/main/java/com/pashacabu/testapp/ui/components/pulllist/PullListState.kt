package com.pashacabu.testapp.ui.components.pulllist

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pashacabu.testapp.extensions.asState
import kotlin.math.absoluteValue

class PullListState(
    val loading: State<Boolean>,
    val topSize: Float,
    val bottomSize: Float
) {

    private val shiftPortion = Animatable(0f)
    val shift = derivedStateOf { shiftPortion.value.coerceIn(0f, 1f) }

    private val _labelToShow = mutableStateOf<LabelType>(LabelType.External)
    val labelToShow = _labelToShow.asState()

    fun setLabel(direction: LabelType) {
        _labelToShow.value = direction
    }

    private val _innerLoading = mutableStateOf(false)
    val innerLoading = _innerLoading.asState()

    fun setInnerLoading(loading: Boolean) {
        _innerLoading.value = loading
    }

    val animateIndicator =
        derivedStateOf { (loading.value || innerLoading.value) && shift.value == 1f }

    suspend fun snapShift(offset: Float) {
        if (offset > 0) {
            shiftPortion.snapTo(offset.absoluteValue / topSize)
        } else {
            shiftPortion.snapTo(offset.absoluteValue / bottomSize)
        }
    }

    suspend fun animateShift(enabled: Boolean) {
        shiftPortion.animateTo(if (enabled) 1f else 0f)
    }

    sealed class LabelType {
        object Top : LabelType()
        object Bottom : LabelType()
        object External : LabelType()
    }
}

@Composable
fun rememberPullListState(
    loading: State<Boolean>,
    topSize: Float,
    bottomSize: Float
): PullListState {
    return remember {
        PullListState(loading, topSize, bottomSize)
    }
}
