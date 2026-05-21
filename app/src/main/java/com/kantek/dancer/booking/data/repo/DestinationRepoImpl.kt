package com.kantek.dancer.booking.data.repo

import com.kantek.dancer.booking.data.local.LanguageLocalSource
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.domain.repo.DestinationRepo
import com.kantek.dancer.booking.presentation.model.support.Screen

class DestinationRepoImpl(
    private val languageLocalSource: LanguageLocalSource,
    private val userLocalSource: UserLocalSource,
) : DestinationRepo {

    override fun getStartDestination(): String {
        return if (languageLocalSource.isShowWelcome) {
            Screen.Language.name
        } else if (userLocalSource.isLogin()) {
            Screen.Home.name
        } else {
            Screen.SignIn.name
        }
    }
}
