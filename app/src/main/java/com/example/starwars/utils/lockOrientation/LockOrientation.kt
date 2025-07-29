package com.example.starwars.utils.lockOrientation

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * A Composable function that locks the screen orientation to portrait mode
 * when the composable enters the composition and is resumed. It then unlocks
 * the orientation (sets it to unspecified) when the composable is disposed.
 *
 * This is useful for screens or parts of an application where a specific
 * orientation is desired. It uses [DisposableEffect] to manage the lifecycle
 * of the orientation lock, ensuring that the orientation is reset when the
 * composable is no longer in use.
 *
 * It observes the [Lifecycle.Event.ON_RESUME] event to apply the orientation lock,
 * as `requestedOrientation` should typically be set when the Activity is in a resumed state.
 */
@Composable
fun LockOrientation() {
    val context = LocalContext.current
    // Attempt to cast the context to an Activity. This will be null if the LocalContext
    // is not an Activity context, in which case orientation locking will not work.
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    // DisposableEffect ties the orientation lock to the lifecycle of this composable.
    // When LockOrientation enters the composition, the effect runs.
    // When LockOrientation leaves the composition, the onDispose block runs.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // Lock orientation to portrait when the composable's lifecycle owner is resumed.
            if (event == Lifecycle.Event.ON_RESUME) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        // Add the observer to the lifecycle of the current composable.
        lifecycleOwner.lifecycle.addObserver(observer)

        // The onDispose block is called when the composable is removed from the composition.
        onDispose {
            // Remove the lifecycle observer to prevent memory leaks.
            lifecycleOwner.lifecycle.removeObserver(observer)
            // Reset the requested orientation to allow the system to determine the best orientation.
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}