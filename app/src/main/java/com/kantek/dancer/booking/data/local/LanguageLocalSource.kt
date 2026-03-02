package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.GsonCaching
import java.util.Locale

class LanguageLocalSource(context: Context) {

    private var caching: GsonCaching = GsonCaching(context)

    private var appLanguage: String by caching.string("app:language", Locale.getDefault().language)
    var isShowWelcome: Boolean by caching.boolean("app:language:welcome", true)

    fun get() = appLanguage

    fun save(language: String?): Boolean {
        return if (!language.isNullOrEmpty() && language != appLanguage) {
            appLanguage = language
            true
        } else false
    }
}