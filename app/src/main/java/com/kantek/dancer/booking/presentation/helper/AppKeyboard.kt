package com.kantek.dancer.booking.presentation.helper

import android.content.Context
import android.view.inputmethod.InputMethodManager

class AppKeyboard(private val activityRetriever: ActivityRetriever) {
    fun hide() {
        val activity = activityRetriever.getOrNull() ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus ?: activity.window.decorView
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}