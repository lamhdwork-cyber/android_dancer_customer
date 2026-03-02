package com.kantek.dancer.booking.presentation.extensions

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import com.kantek.dancer.booking.domain.extension.getBy
import org.koin.compose.getKoin
import org.koin.core.scope.Scope

@Composable
inline fun <reified T : Any> Scope.use(scopeName: CharSequence? = null): T {
    return remember { scopeName?.let { getBy(it) } ?: get() }
}

@Composable
fun useScope(scopeName: CharSequence): Scope {
    val scopeId = "rememberScope:$scopeName"
    val koin = getKoin()
    return remember {
        koin.getOrCreateScope<Any>(scopeId)
    }
}

@Composable
fun ScopeProvider(
    scopeName: CharSequence? = null,
    block: @Composable Scope.() -> Unit = {}
) {
    val scopeName1 = scopeName ?: currentCompositeKeyHash.toString(36)
    val scope = useScope(scopeName1)
    useScopeDisposableEffect {
        Log.e("CloseScope", "Close $scopeName1")
        scope.close()
    }
    block(scope)
}

@Composable
private fun useScopeDisposableEffect(function: () -> Unit) {
    val savable = LocalSavedStateRegistryOwner.current
    val finalKey = "useDisposableEffect:${currentCompositeKeyHash.toString(36)}"
    remember(savable) {
        var isSavedState = false
        var isRegistered = false
        try {
            savable.savedStateRegistry.registerSavedStateProvider(finalKey) {
                isSavedState = true
                Bundle()
            }
            isRegistered = true
        } catch (_: IllegalArgumentException) {
        }
        object : RememberObserver {
            override fun onRemembered() {}
            override fun onAbandoned() {}

            override fun onForgotten() {
                if (isRegistered) savable.savedStateRegistry.unregisterSavedStateProvider(finalKey)
                dispose()
            }

            private fun dispose() {
                if (!isSavedState) {
                    function()
                }
            }
        }
    }
}