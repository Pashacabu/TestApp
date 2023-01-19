package com.pashacabu.testapp.extensions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

fun <T> MutableState<T>.asState(): State<T> = this