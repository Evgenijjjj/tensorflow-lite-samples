package com.evgeny.archBase.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun DisposableEffectWithLifeCycle(
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onStart: () -> Unit = {},
    onCreate: () -> Unit = {},
    onDestroy: () -> Unit = {},
) {
    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner = LocalLifecycleOwner.current

    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnPause by rememberUpdatedState(onPause)
    val currentOnStop by rememberUpdatedState(onStop)
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnCreate by rememberUpdatedState(onCreate)
    val currentOnDestroy by rememberUpdatedState(onDestroy)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    currentOnCreate()
                }

                Lifecycle.Event.ON_START -> {
                    currentOnStart()
                }

                Lifecycle.Event.ON_RESUME -> {
                    currentOnResume()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    currentOnPause()
                }

                Lifecycle.Event.ON_STOP -> {
                    currentOnStop()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    currentOnDestroy()
                }

                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
