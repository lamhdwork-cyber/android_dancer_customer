package android.support.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
private fun SetSystemBarsColor(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent,
    statusBarDarkIcons: Boolean = false,
    navigationBarDarkIcons: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor, darkIcons = statusBarDarkIcons)
        systemUiController.setNavigationBarColor(
            navigationBarColor,
            darkIcons = navigationBarDarkIcons
        )
    }
}

/** Auth flows with light backgrounds (sign up, forgot password): restore light status bar after dark screens. */
@Composable
fun ApplyLightStatusBarsForAuthScreens() {
    SetSystemBarsColor(
        statusBarColor = Color.White,
        navigationBarColor = Color.Black,
        statusBarDarkIcons = true,
        navigationBarDarkIcons = false
    )
}

/** Dark auth UI (e.g. guest sign-in): transparent status bar + light status icons for edge-to-edge. */
@Composable
fun ApplyDarkEdgeToEdgeStatusBars() {
    SetSystemBarsColor(
        statusBarColor = Color.Transparent,
        navigationBarColor = Color.Transparent,
        statusBarDarkIcons = false,
        navigationBarDarkIcons = false
    )
}