package com.hdl.dancer.booking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hdl.dancer.booking.presentation.model.support.Screen
import com.hdl.dancer.booking.domain.usecase.GetStartDestinationCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainVM(getStartDestinationCase: GetStartDestinationCase) : ViewModel() {
    private val _startDestination = MutableStateFlow(Screen.Language.name)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    init {
        _startDestination.value = getStartDestinationCase()
    }
}