package android.support.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CoreColors.Primary,
    background = CoreColors.Background,
    surface = CoreColors.Background,
)

/**
 * Minimal dark Material theme used by the reusable widgets. Apps may keep it or
 * wrap content in their own theme by overriding [android.support.ui.app.BaseComponentAct.AppContentTheme].
 */
@Composable
fun BaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColorScheme, content = content)
}
