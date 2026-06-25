package com.kantek.dancer.booking.app

import android.support.ui.app.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.data.model.response.UserDTO
import com.kantek.dancer.booking.presentation.viewmodel.FetchUserRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.java.KoinJavaComponent.inject

abstract class AppViewModel : BaseViewModel() {
    var currentLanguageBackup = ""
    var currentUserBackup: UserDTO? = null

    private val languageRepo: LanguageRepo by inject(LanguageRepo::class.java)
    private val fetchUserRepo: FetchUserRepo by inject(FetchUserRepo::class.java)
    val userLive = fetchUserRepo.live().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun getCurrentLanguage(): String {
        return languageRepo.getCurrent()
    }

    fun getCurrentUser(): UserDTO? {
        return fetchUserRepo.currentDTO()
    }
}