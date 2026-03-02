package com.kantek.dancer.booking.data.repo

import android.content.Context
import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.factory.LanguageFactory
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage

class LanguageRepo(
    private val userApi: UserApi,
    private val languageLocalSource: LanguageLocalSource,
    private val userLocalSource: UserLocalSource,
    private val languageFactory: LanguageFactory
) {
    fun fetchAll(context: Context?=null): List<ILanguage> {
        return languageFactory.mocks(context)
    }

    fun getCurrent(): String {
        return languageLocalSource.get()
    }

    suspend fun save(language: String): Boolean {
        if (getCurrent() == language) return false
        if (userLocalSource.isLogin()) {
            userApi.changeLanguage(language).await()
            userLocalSource.postLive()
        }
        return languageLocalSource.save(language)
    }

    fun markForWelcome() {
        languageLocalSource.isShowWelcome = false
    }

    fun getLanguageDisplay(): Int {
        return languageFactory.getLanguageDisplay(languageLocalSource.get())
    }
}