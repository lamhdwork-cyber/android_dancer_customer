package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.domain.model.support.Screen

class GetStartDestinationMainRepo(
    private val languageLocalSource: LanguageLocalSource,
    private val userLocalSource: UserLocalSource
) {
    operator fun invoke(): String {
        return if (languageLocalSource.isShowWelcome) {
            Screen.Language.name
        } else if (userLocalSource.isLogin())
            Screen.Home.name
        else Screen.SignIn.name
    }
}