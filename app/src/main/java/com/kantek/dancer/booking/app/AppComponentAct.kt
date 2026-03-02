package com.kantek.dancer.booking.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.support.core.event.StateFlowStatusOwner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.data.extensions.updateLocale
import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.presentation.AuthAct
import com.kantek.dancer.booking.presentation.theme.AppTheme
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.LoadingView
import com.kantek.dancer.booking.presentation.widget.SetSystemBarsColor
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import java.util.Locale

abstract class AppComponentAct : ComponentActivity(), AppErrorHandler by AppErrorHandlerImpl() {
    private val windowStatus = WindowStatusProvider.instance
    private val userLocalSource: UserLocalSource by inject()
    private val languageLocalSource: LanguageLocalSource by inject()
    private var notificationDialog = mutableStateOf<String?>(null)
    private var exitAppDialog = mutableStateOf<Boolean?>(false)
    private var expiredTokenDialog = mutableStateOf<Boolean?>(false)
    private var mHasKillApp = false

    override fun attachBaseContext(newBase: Context) {
        val localeUpdatedContext: ContextWrapper =
            newBase.updateLocale(Locale(languageLocalSource.get()))
        super.attachBaseContext(localeUpdatedContext)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent { Content() }
    }

    fun showNotification(message: String) {
        mHasKillApp = false
        notificationDialog.value = message
    }

    fun showNotification(messageRes: Int, hasKillApp: Boolean = false) {
        mHasKillApp = hasKillApp
        notificationDialog.value = getString(messageRes)
    }

    private fun dismissNotification(hasKillApp: Boolean) {
        if (hasKillApp)
            finishAndRemoveTask()
        notificationDialog.value = null
    }

    private fun exitApp(hasKillApp: Boolean) {
        if (hasKillApp)
            finishAndRemoveTask()
        exitAppDialog.value = null
    }

    fun showExitAppDialog() {
        mHasKillApp = true
        exitAppDialog.value = true
    }

    fun showExpiredTokenDialog(hasShow: Boolean?) {
        expiredTokenDialog.value = hasShow
    }

    private fun openLogin() {
        userLocalSource.logout()
        val intent = Intent(this, AuthAct::class.java)
        startActivity(intent)
    }

    @Composable
    fun Content() {
        notificationDialog.value?.let { message ->
            AppNotificationDialog(message, mHasKillApp) { dismissNotification(it) }
        }
        exitAppDialog.value?.let { isShow ->
            if (isShow)
                AppConfirmDialog(
                    message = stringResource(R.string.msg_exit_app),
                    textConfirm = stringResource(R.string.all_exit),
                    onConfirm = {
                        exitApp(true)
                    }, onDismiss = {
                        exitApp(false)
                    }
                )
        }
        expiredTokenDialog.value?.let { isShow ->
            if (isShow)
                AppConfirmDialog(
                    title = stringResource(R.string.auth_title_token_expired),
                    message = stringResource(R.string.auth_msg_token_expired),
                    textConfirm = stringResource(R.string.btn_relogin),
                    onConfirm = {
                        showExpiredTokenDialog(null)
                        openLogin()
                    }, onDismiss = {
                        showExpiredTokenDialog(null)
                    }
                )
        }

        ObserveWindowStatus()

        SetSystemBarsColor()
        KoinContext {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    color = Color.White,
                ) {
                    ProvideContent()
                }
            }
        }
    }

    @Composable
    private fun ObserveWindowStatus() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val isLoading = remember { mutableStateOf(false) }
        windowStatus.loading.observe(lifecycleOwner) {
            isLoading.value = it
        }
        windowStatus.error.observe(lifecycleOwner) {
            handle(this@AppComponentAct, it)
        }
        LoadingView(isLoading.value)
    }

    @Composable
    abstract fun ProvideContent()

    object WindowStatusProvider {
        val instance: StateFlowStatusOwner by lazy { StateFlowStatusOwner() }
    }
}