package android.support.ui.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.core.event.StateFlowStatusOwner
import android.support.core.extensions.updateLocale
import android.support.ui.R
import android.support.ui.theme.BaseTheme
import android.support.ui.theme.CoreColors
import android.support.ui.widget.AppConfirmDialog
import android.support.ui.widget.AppNotificationDialog
import android.support.ui.widget.LoadingView
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import java.util.Locale

/**
 * Reusable Compose activity scaffolding: edge-to-edge window setup, a shared
 * [windowStatus] owner, loading + error observation, and the common
 * notification / exit / expired-token dialogs. Designed to be copied into a new
 * project as-is; the only app-specific bits are exposed as overridable hooks
 * ([currentLanguage], [onExpiredToken], [errorHandler], [AppContentTheme]).
 */
abstract class BaseComponentAct : ComponentActivity(),
    AppErrorHandler by AppErrorHandlerImpl() {
    protected val windowStatus: StateFlowStatusOwner = WindowStatusProvider.instance

    private var notificationDialog = mutableStateOf<String?>(null)
    private var exitAppDialog = mutableStateOf<Boolean?>(false)
    private var expiredTokenDialog = mutableStateOf<Boolean?>(false)
    private var mHasKillApp = false

    /** App with multi-language support overrides this. Defaults to English. */
    protected open fun currentLanguage(): String = "en"

    /** Called when the session is expired and the user confirms re-login.
     *  App overrides to clear cache and open the login screen. */
    protected open fun onExpiredToken() {}

    override fun attachBaseContext(newBase: Context) {
        val localeUpdatedContext: ContextWrapper =
            newBase.updateLocale(Locale.forLanguageTag(currentLanguage()))
        super.attachBaseContext(localeUpdatedContext)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT),
        )
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent { Content() }
    }

    fun showNotification(message: String) {
        mHasKillApp = false
        notificationDialog.value = message
    }

    fun showNotification(messageRes: Int) {
        mHasKillApp = false
        notificationDialog.value = getString(messageRes)
    }

    private fun dismissNotification(hasKillApp: Boolean) {
        if (hasKillApp) finishAndRemoveTask()
        notificationDialog.value = null
    }

    private fun exitApp(hasKillApp: Boolean) {
        if (hasKillApp) finishAndRemoveTask()
        exitAppDialog.value = null
    }

    fun showExitAppDialog() {
        mHasKillApp = true
        exitAppDialog.value = true
    }

    fun showExpiredTokenDialog(hasShow: Boolean?) {
        expiredTokenDialog.value = hasShow
    }

    @Composable
    private fun Content() {
        notificationDialog.value?.let { message ->
            AppNotificationDialog(message, mHasKillApp) { dismissNotification(it) }
        }
        exitAppDialog.value?.let { isShow ->
            if (isShow)
                AppConfirmDialog(
                    message = stringResource(R.string.core_msg_exit_app),
                    textConfirm = stringResource(R.string.core_all_exit),
                    onConfirm = { exitApp(true) },
                    onDismiss = { exitApp(false) }
                )
        }
        expiredTokenDialog.value?.let { isShow ->
            if (isShow)
                AppConfirmDialog(
                    title = stringResource(R.string.core_auth_title_token_expired),
                    message = stringResource(R.string.core_auth_msg_token_expired),
                    textConfirm = stringResource(R.string.core_btn_re_login),
                    onConfirm = {
                        showExpiredTokenDialog(null)
                        onExpiredToken()
                    },
                    onDismiss = { showExpiredTokenDialog(null) }
                )
        }

        ObserveWindowStatus()

        AppContentTheme {
            Surface(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .background(CoreColors.Background),
                color = CoreColors.Background,
            ) {
                ProvideContent()
            }
        }
    }

    @Composable
    private fun ObserveWindowStatus() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val isLoading = remember { mutableStateOf(false) }
        windowStatus.loading.observe(lifecycleOwner) { isLoading.value = it }
        windowStatus.error.observe(lifecycleOwner) { handle(this@BaseComponentAct, it) }
        LoadingView(isLoading.value)
    }

    /** Theme wrapper. App can override to apply its own Material theme. */
    @Composable
    protected open fun AppContentTheme(content: @Composable () -> Unit) = BaseTheme(content)

    @Composable
    protected abstract fun ProvideContent()

    object WindowStatusProvider {
        val instance: StateFlowStatusOwner by lazy { StateFlowStatusOwner() }
    }
}