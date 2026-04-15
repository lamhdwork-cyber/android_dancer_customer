package com.kantek.dancer.booking.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.presentation.screen.introduce.IntroduceScreen
import com.kantek.dancer.booking.presentation.screen.language.LanguageScreen

class LanguageSelectorAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        val hasCompleteIntroduce = remember { mutableStateOf(false) }
        if (hasCompleteIntroduce.value) {
            LanguageScreen(false)
        } else {
            IntroduceScreen {
                hasCompleteIntroduce.value = true
            }
        }
    }
}