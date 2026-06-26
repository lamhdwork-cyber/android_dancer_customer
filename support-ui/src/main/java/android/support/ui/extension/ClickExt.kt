package android.support.ui.extension

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/** Default debounce window for clicks (ms). 1000 recommended for heavy/irreversible actions. */
const val CLICK_DEBOUNCE_MS = 500L

/**
 * Debounced clickable: ignores taps within [debounceMs] of the previous accepted
 * tap. Drop-in replacement for [clickable] (also supports a custom
 * [interactionSource] / [indication] like the ripple).
 */
fun Modifier.onClick(
    debounceMs: Long = CLICK_DEBOUNCE_MS,
    interactionSource: MutableInteractionSource? = null,
    indication: Indication? = null,
    onClick: () -> Unit
): Modifier = composed {
    var last by remember { mutableLongStateOf(0L) }
    val debounced: () -> Unit = {
        val now = System.currentTimeMillis()
        if (now - last >= debounceMs) {
            last = now
            onClick()
        }
    }
    if (interactionSource != null) {
        clickable(
            interactionSource = interactionSource,
            indication = indication,
            onClick = debounced
        )
    } else {
        clickable(onClick = debounced)
    }
}

/**
 * Wraps an `onClick` lambda with debounce, for APIs that take an onClick directly
 * (e.g. `Button(onClick = rememberDebouncedClick { ... })`).
 */
@Composable
fun rememberDebouncedClick(
    debounceMs: Long = CLICK_DEBOUNCE_MS,
    onClick: () -> Unit
): () -> Unit {
    var last by remember { mutableLongStateOf(0L) }
    return {
        val now = System.currentTimeMillis()
        if (now - last >= debounceMs) {
            last = now
            onClick()
        }
    }
}
