package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.datastore.GsonDataStoreCaching
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class LanguageLocalSource(context: Context) {

    private val caching = GsonDataStoreCaching(context)

    val isShowWelcome = caching.boolean("app:language:welcome", true)

    fun get(): String {
        val tag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        return tag.ifEmpty { "en" }.substringBefore('-')
    }

    fun save(language: String?): Boolean {
        return if (!language.isNullOrEmpty() && language != get()) {
            applyLocale(language)
            true
        } else false
    }

    private fun applyLocale(language: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
    }
}
