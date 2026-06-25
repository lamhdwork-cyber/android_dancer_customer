package android.support.core.extensions

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
    val configuration: Configuration = this.resources.configuration
    val localeList = LocaleList(localeToSwitchTo)
    LocaleList.setDefault(localeList)
    configuration.setLocales(localeList)
    val context = this.createConfigurationContext(configuration)
    return ContextWrapper(context)
}