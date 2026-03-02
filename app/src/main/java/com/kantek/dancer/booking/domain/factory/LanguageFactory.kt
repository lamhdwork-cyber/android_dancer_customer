package com.kantek.dancer.booking.domain.factory

import android.content.Context
import androidx.annotation.StringRes
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage

class LanguageFactory {
    fun mocks(context: Context? = null): List<ILanguage> {
        return listOf(object : ILanguage {
            override val id: Int
                get() = 1
            override val nameRes: Int
                get() = R.string.language_en
            override val code: String
                get() = AppConfig.Languages.EN

            override fun toString(): String {
                return context?.getString(nameRes) ?: ""
            }
        }, object : ILanguage {
            override val id: Int
                get() = 2
            override val nameRes: Int
                get() = R.string.language_vi
            override val code: String
                get() = AppConfig.Languages.VI

            override fun toString(): String {
                return context?.getString(nameRes) ?: ""
            }
        }, object : ILanguage {
            override val id: Int
                get() = 3
            override val nameRes: Int
                get() = R.string.language_es
            override val code: String
                get() = AppConfig.Languages.ES

            override fun toString(): String {
                return context?.getString(nameRes) ?: ""
            }
        }
        )
    }

    @StringRes
    fun getLanguageDisplay(languageCode: String): Int {
        return when (languageCode) {
            AppConfig.Languages.VI -> R.string.language_vi
            AppConfig.Languages.ES -> R.string.language_es
            else -> R.string.language_en
        }
    }
}