package com.kantek.dancer.booking.presentation.viewmodel

import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage
import com.kantek.dancer.booking.presentation.extensions.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageVM(private val languageRepo: LanguageRepo) : AppViewModel() {
    private val _selectedLanguage = MutableStateFlow(languageRepo.getCurrent())
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _items = MutableStateFlow<List<ILanguage>>(emptyList())
    val items: StateFlow<List<ILanguage>> = _items

    val onContinueSuccess = MutableStateFlow(false)
    val onChangeSuccess = MutableStateFlow(false to false)

    fun setLanguage(language: String) = launch(loading, error) {
        _selectedLanguage.value = language
    }

    fun saveLanguage(hasContinue: Boolean = true) = launch(loading, error) {
        val hasChange = languageRepo.save(_selectedLanguage.value)
        if (hasContinue)
            onContinueSuccess.value = true
        else onChangeSuccess.value = true to hasChange
    }

    fun onFetch() = launch(loading, error) {
        _items.value = languageRepo.fetchAll()
    }

    fun markForWelcome() = launch(loading, error) {
        languageRepo.markForWelcome()
    }
}