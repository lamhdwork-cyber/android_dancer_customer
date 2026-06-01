package com.kantek.dancer.booking.presentation.viewmodel

import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.repo.SignInRepo
import com.kantek.dancer.booking.domain.model.user.SignInForm
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ManageStaffSignInVM(
    private val appKeyboard: AppKeyboard,
    private val signInRepo: SignInRepo
) : AppViewModel() {
    private val _form = MutableStateFlow(SignInForm())
    val formState: StateFlow<SignInForm> = _form
    val loginSuccess = MutableStateFlow(false)

    init {
        _form.value.apply {
            account = "maria@dancer.local"
            password = "Dancer@123"
        }
    }

    fun updateAccount(value: String) {
        if (_form.value.account != value)
            _form.value = _form.value.copy(account = value)
    }

    fun updatePassword(value: String) {
        if (_form.value.password != value)
            _form.value = _form.value.copy(password = value)
    }

    fun signIn() = launch(loading, error) {
        appKeyboard.hide()
        _form.value.validate()
        signInRepo(_form.value)
        loginSuccess.emit(true)
    }
}
