package com.kantek.dancer.booking.data.local

import android.content.Context
import android.support.persistent.cache.datastore.GsonDataStoreCaching
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageLocalSource(context: Context) {

    private val caching = GsonDataStoreCaching(context)

    val isShowWelcome = caching.boolean("app:language:welcome", true)

    private val _currentLanguage = MutableStateFlow(get())

    fun get(): String {
        val tag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        return tag.ifEmpty { "en" }.substringBefore('-')
    }

    fun languageFlow() = _currentLanguage.asStateFlow()

    fun save(language: String?): Boolean {
        return if (!language.isNullOrEmpty() && language != get()) {
            applyLocale(language)
            _currentLanguage.value = language
            true
        } else false
    }

    private fun applyLocale(language: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
    }
}
