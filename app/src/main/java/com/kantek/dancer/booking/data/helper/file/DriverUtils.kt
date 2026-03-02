package com.kantek.dancer.booking.data.helper.file

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi


object DriverUtils {


    fun sendEmail(email: String, subject: String?, message: String?): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        return intent
    }

    private fun openIntent(context: Context, intent: Intent) {
        try {
            context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            exception.printStackTrace()
        }
    }

    private fun uri(uri: String): Uri {
        return Uri.parse(uri)
    }

    private fun intent(uri: Uri): Intent {
        return Intent(Intent.ACTION_VIEW, uri)
    }

    fun navigateMyLocationWithGoogleMap(context: Context, latitude: Double, longitude: Double) {
        val mapIntent = intent(uri("google.navigation:q=$latitude,$longitude"))
        mapIntent.setPackage("com.google.android.apps.maps")
        openIntent(context, mapIntent)
    }

    fun openWebsite(context: Context, url: String) {
        openIntent(context, intent(uri(url)))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun openSettingNotifications(context: Context) {
        val settingsIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        context.startActivity(settingsIntent)
    }
}


