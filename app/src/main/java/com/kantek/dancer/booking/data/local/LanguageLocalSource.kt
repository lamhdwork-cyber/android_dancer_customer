package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.sharepreferences.GsonCaching
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * UI locale is owned by AndroidX per-app language
 * ([AppCompatDelegate.setApplicationLocales]): the system persists it and
 * applies it automatically (framework on API 33+, AppCompat backport below),
 * so there is no manual `attachBaseContext` / DataStore handling here.
 *
 * Only the unrelated [isShowWelcome] flag still lives in SharedPreferences.
 */
class LanguageLocalSource(context: Context) {

    private var caching: GsonCaching = GsonCaching(context)

    var isShowWelcome: Boolean by caching.boolean("app:language:welcome", true)

    init {
        // One-time migration: carry the legacy "app:language" pref into the
        // per-app locale store so existing users keep their chosen language.
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            val legacy = caching.shared.getString("app:language", "").orEmpty()
            if (legacy.isNotEmpty()) applyLocale(legacy)
        }
    }

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
