package com.kantek.dancer.booking.presentation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.presentation.screen.introduce.IntroduceScreen

class AppIntroduceAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        val hasCompleteIntroduce = remember { mutableStateOf(false) }
        LaunchedEffect(hasCompleteIntroduce.value) {
            if (hasCompleteIntroduce.value) {
                openAuth()
            }
        }
        if (!hasCompleteIntroduce.value) {
            IntroduceScreen { hasCompleteIntroduce.value = true }
        }
    }

    private fun openAuth() {
        val intent = Intent(this, AuthAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
