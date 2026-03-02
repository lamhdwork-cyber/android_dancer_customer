package com.kantek.dancer.booking.presentation.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.kantek.dancer.booking.R

fun Context.copyText(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("copied_text", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, getString(R.string.all_copied), Toast.LENGTH_SHORT).show()
}