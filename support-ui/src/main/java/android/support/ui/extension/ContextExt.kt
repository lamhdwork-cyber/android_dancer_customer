package android.support.ui.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.ui.R
import android.widget.Toast

fun Context.copyText(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("copied_text", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, getString(R.string.all_copied), Toast.LENGTH_SHORT).show()
}