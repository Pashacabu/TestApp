package com.pashacabu.testapp.ui.components.pulllist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.sign

class PullListScrollConnection(
    private val pullListState: PullListState,
    private val coroutineScope: CoroutineScope,
    private val topAction: () -> Unit = {},
    private val bottomAction: () -> Unit = {},
    private val touchMultiplier: Float = 1 / 3f,
    private val refreshOnly: State<Boolean>
) : NestedScrollConnection {

    private var swipeInProgress = false
    private var nonMultipliedOffset = 0f
    private var lastOffsetSign = 0f

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return when {
            source == NestedScrollSource.Drag && swipeInProgress -> onSwipe(available)
            else -> Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        Timber.d("postScroll: available = ${available.y}")
        Timber.d("postScroll: consumed = ${consumed.y}")
        return when {
            source == NestedScrollSource.Drag && available.y != 0f -> {
                swipeInProgress = true
                onSwipe(available)
            }
            else -> Offset.Zero
        }
    }

    private fun onSwipe(available: Offset): Offset {
        return if (swipeInProgress) {
            if (refreshOnly.value && available.y < 0 && nonMultipliedOffset <= 0f) {
                Offset.Zero
            } else {
                if (nonMultipliedOffset.sign + available.y.sign == 0f || pullListState.shift.value <= 1) {
                    nonMultipliedOffset += available.y
                }
                val newSign = nonMultipliedOffset.sign
                if (lastOffsetSign + newSign == 0f) {
                    exitSwipe(available)
                } else {
                    lastOffsetSign = newSign
                    pullListState.setLabel(
                        when {
                            nonMultipliedOffset > 0f -> PullListState.LabelType.Top
                            nonMultipliedOffset == 0f -> PullListState.LabelType.External
                            else -> PullListState.LabelType.Bottom
                        }
                    )
                    val newOffset = nonMultipliedOffset * touchMultiplier
                    coroutineScope.launch {
                        pullListState.snapShift(newOffset)
                    }
                    available
                }
            }
        } else {
            Offset.Zero
        }
    }

    private fun exitSwipe(available: Offset): Offset {
        swipeInProgress = false
        nonMultipliedOffset = 0f
        lastOffsetSign = nonMultipliedOffset.sign
        coroutineScope.launch {
            pullListState.snapShift(0f)
            pullListState.setLabel(PullListState.LabelType.External)
        }
        return available
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        if (!pullListState.loading.value) {
            if (pullListState.shift.value < 0.75f) {
                coroutineScope.launch { pullListState.animateShift(false) }
            } else {
                pullListState.setInnerLoading(true)
                coroutineScope.launch { pullListState.animateShift(true) }
                when (pullListState.labelToShow.value) {
                    PullListState.LabelType.Top -> topAction.invoke()
                    PullListState.LabelType.Bottom -> bottomAction.invoke()
                    else -> {}
                }
            }
        }
        swipeInProgress = false
        val needToConsumeFling = nonMultipliedOffset != 0f
        nonMultipliedOffset = 0f
        return if (needToConsumeFling) available else Velocity.Zero
    }
}

@Composable
fun rememberPullListScrollConnection(
    pullListState: PullListState,
    coroutineScope: CoroutineScope,
    topAction: () -> Unit,
    bottomAction: () -> Unit,
    touchMultiplier: Float = 1 / 3f,
    refreshOnly: State<Boolean>
): PullListScrollConnection {
    return remember {
        PullListScrollConnection(
            pullListState,
            coroutineScope,
            topAction,
            bottomAction,
            touchMultiplier,
            refreshOnly
        )
    }
}
