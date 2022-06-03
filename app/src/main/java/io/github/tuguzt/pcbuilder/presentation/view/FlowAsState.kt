package io.github.tuguzt.pcbuilder.presentation.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Remembers [Flow] by [flow] and [lifecycleOwner],
 * making it lifecycle aware by [flowWithLifecycle] extension function.
 */
@Composable
private fun <T> rememberFlowLifecycleAware(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
): Flow<T> = remember(flow, lifecycleOwner) {
    flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
}

/**
 * Collects lifecycle aware [Flow] as [State].
 */
@Composable
fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext,
): State<R> = rememberFlowLifecycleAware(flow = this).collectAsState(initial, context)

/**
 * Collects lifecycle aware [StateFlow] as [State].
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> StateFlow<T>.collectAsStateLifecycleAware(
    context: CoroutineContext = EmptyCoroutineContext,
): State<T> = collectAsStateLifecycleAware(initial = value, context = context)
