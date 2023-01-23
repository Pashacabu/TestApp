package com.pashacabu.testapp.ui.components.pulllist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PullList(
    modifier: Modifier = Modifier,
    loadingState: State<Boolean>,
    topAction: () -> Unit = {},
    bottomAction: () -> Unit = {},
    topSize: Dp = 24.dp,
    bottomSize: Dp = 24.dp,
    touchMultiplier: Float = 1 / 3f,
    indicator: @Composable (PullListState) -> Unit,
    moveContentWithIndicator: Boolean = true,
    onlyRefresh: State<Boolean> = mutableStateOf(true),
    content: @Composable () -> Unit
) {
    val pullListState = rememberPullListState(
        loading = loadingState,
        topSize = with(LocalDensity.current) { topSize.toPx() },
        bottomSize = with(LocalDensity.current) { bottomSize.toPx() }
    )
    val connection = rememberPullListScrollConnection(
        pullListState = pullListState,
        coroutineScope = rememberCoroutineScope(),
        topAction = topAction,
        bottomAction = bottomAction,
        touchMultiplier = touchMultiplier,
        refreshOnly = onlyRefresh
    )
    LaunchedEffect(key1 = pullListState.loading.value) {
        if (!pullListState.loading.value) {
            pullListState.animateShift(false)
            pullListState.setLabel(PullListState.LabelType.External)
            pullListState.setInnerLoading(false)
        } else {
            if (!pullListState.innerLoading.value) {
                pullListState.setLabel(PullListState.LabelType.External)
                pullListState.animateShift(true)
            }
        }
    }
    Box(modifier = modifier) {
        Surface(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(connection = connection)
                    .graphicsLayer {
                        translationY = if (moveContentWithIndicator) {
                            when (pullListState.labelToShow.value) {
                                PullListState.LabelType.Top -> (pullListState.shift.value * topSize.value * 2).dp.toPx()
                                PullListState.LabelType.Bottom -> (pullListState.shift.value * bottomSize.value * (-2)).dp.toPx()
                                PullListState.LabelType.External -> (pullListState.shift.value * topSize.value * 2).dp.toPx()
                            }
                        } else 0f
                    }
            ) {
                content()
            }
        }
        if (pullListState.shift.value != 0f) {
            Box(
                modifier = Modifier
                    .align(
                        when (pullListState.labelToShow.value) {
                            PullListState.LabelType.Bottom -> Alignment.BottomCenter
                            PullListState.LabelType.External -> Alignment.TopCenter
                            PullListState.LabelType.Top -> Alignment.TopCenter
                        }
                    )
                    .graphicsLayer {
                        translationY = when (pullListState.labelToShow.value) {
                            PullListState.LabelType.Bottom -> {
                                if (moveContentWithIndicator) {
                                    -pullListState.bottomSize * pullListState.shift.value / 2
                                } else {
                                    -pullListState.bottomSize * pullListState.shift.value
                                }
                            }
                            PullListState.LabelType.External -> {
                                if (moveContentWithIndicator) {
                                    pullListState.topSize * pullListState.shift.value / 2
                                } else {
                                    pullListState.topSize * pullListState.shift.value
                                }
                            }
                            PullListState.LabelType.Top -> {
                                if (moveContentWithIndicator) {
                                    pullListState.topSize * pullListState.shift.value / 2
                                } else {
                                    pullListState.topSize * pullListState.shift.value
                                }
                            }
                        }
                        alpha = pullListState.shift.value
                    }
            ) {
                indicator(pullListState)
            }
        }
    }
}
