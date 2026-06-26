package com.kantek.dancer.booking.app

import android.content.Intent
import android.support.ui.app.BaseComponentAct
import androidx.compose.runtime.Composable
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.presentation.AuthAct
import android.support.ui.extension.ApplyDarkEdgeToEdgeStatusBars
import com.kantek.dancer.booking.presentation.theme.AppTheme
import org.koin.android.ext.android.inject

abstract class AppComponentAct : BaseComponentAct() {
    private val userLocalSource: UserLocalSource by inject()

    override fun onExpiredToken() {
        userLocalSource.logout()
        val intent = Intent(this, AuthAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    @Composable
    override fun AppContentTheme(content: @Composable () -> Unit) {
        ApplyDarkEdgeToEdgeStatusBars()
        AppTheme { content() }
    }
}