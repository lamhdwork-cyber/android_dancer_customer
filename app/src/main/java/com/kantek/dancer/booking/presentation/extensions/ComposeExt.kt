package com.kantek.dancer.booking.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver


@Composable
fun useEffect(vararg dependencies: Any, function: () -> Unit) {
    remember(*dependencies) {
        object : RememberObserver {
            override fun onRemembered() {
                function()
            }

            override fun onAbandoned() {}
            override fun onForgotten() {}
        }
    }
}


@Composable
fun useRemember(
    vararg dependencies: Any,
    onForgotten: () -> Unit = {},
    onRemembered: () -> Unit = {}
) {
    remember(*dependencies) {
        object : RememberObserver {
            override fun onRemembered() {
                onRemembered()
            }

            override fun onAbandoned() {
                onForgotten()
            }

            override fun onForgotten() {
                onForgotten()
            }
        }
    }
}

@Composable
fun useDispose(vararg dependencies: Any, function: () -> Unit) {
    remember(*dependencies) {
        object : RememberObserver {
            override fun onRemembered() {

            }

            override fun onAbandoned() {}
            override fun onForgotten() {
                function()
            }
        }
    }
}

@Composable
fun OnResumeEffect(
    key: Any? = Unit,
    onResume: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, key2 = key) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

