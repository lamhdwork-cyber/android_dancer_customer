package com.kantek.dancer.booking.presentation

import androidx.compose.runtime.Composable
import com.kantek.dancer.booking.app.AppComponentAct
import com.kantek.dancer.booking.presentation.screen.language.LanguageScreen

class LanguageSelectorAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        LanguageScreen(false)
    }
}