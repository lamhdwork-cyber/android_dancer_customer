package android.support.ui.extension

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Controls only the status / navigation bar icon appearance (light vs dark icons).
 * In edge-to-edge the bar background comes from the screen content drawn behind the
 * transparent system bars, so no bar color is set here.
 */
@Composable
private fun SetSystemBarsAppearance(
    lightStatusBars: Boolean,
    lightNavigationBars: Boolean,
) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = lightStatusBars
            isAppearanceLightNavigationBars = lightNavigationBars
        }
    }
}

/** Auth flows with light backgrounds (sign up, forgot password): dark status icons. */
@Composable
fun ApplyLightStatusBarsForAuthScreens() {
    SetSystemBarsAppearance(
        lightStatusBars = true,
        lightNavigationBars = false
    )
}

/** Dark auth UI (e.g. guest sign-in): light status icons for edge-to-edge. */
@Composable
fun ApplyDarkEdgeToEdgeStatusBars() {
    SetSystemBarsAppearance(
        lightStatusBars = false,
        lightNavigationBars = false
    )
}
