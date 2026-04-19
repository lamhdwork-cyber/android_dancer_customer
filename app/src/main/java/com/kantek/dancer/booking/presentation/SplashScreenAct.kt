package com.kantek.dancer.booking.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenAct : AppComponentAct() {

    private val languageLocalSource: LanguageLocalSource by inject()
    private val userLocalSource: UserLocalSource by inject()
    private var hasKeep = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { hasKeep }
        }
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            if (languageLocalSource.isShowWelcome) {
                hasKeep = false
                delay(500)
                openAppIntroduce()
            } else {
                if (userLocalSource.isLogin())
                    openMain()
                else openAuth()
                hasKeep = false
            }
        }
    }

    private fun openAuth() {
        val intent = Intent(this, AuthAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun openLanguage() {
        val intent = Intent(this, LanguageSelectorAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun openAppIntroduce() {
        val intent = Intent(this, AppIntroduceAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun openMain() {
        val intent = Intent(this, MainAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    @Composable
    override fun ProvideContent() {
    }
}