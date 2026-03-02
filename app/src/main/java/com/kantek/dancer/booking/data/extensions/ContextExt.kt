package com.kantek.dancer.booking.data.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import java.util.Locale


@SuppressLint("HardwareIds")
fun Context.getDeviceID(): String {
    return Settings.Secure.getString(
        this.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

fun Context.updateLocale(localeToSwitchTo: Locale): ContextWrapper {
    var context = this
    val resources: Resources = this.resources
    val configuration: Configuration = resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    } else {
        configuration.locale = localeToSwitchTo
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        context = this.createConfigurationContext(configuration)
    } else {
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    return ContextWrapper(context)
}