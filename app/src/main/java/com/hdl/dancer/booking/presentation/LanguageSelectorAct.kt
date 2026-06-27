package com.hdl.dancer.booking.presentation

import androidx.compose.runtime.Composable
import com.hdl.dancer.booking.app.AppComponentAct
import com.hdl.dancer.booking.presentation.screen.language.LanguageScreen

class LanguageSelectorAct : AppComponentAct() {
    @Composable
    override fun ProvideContent() {
        LanguageScreen(false)
    }
}