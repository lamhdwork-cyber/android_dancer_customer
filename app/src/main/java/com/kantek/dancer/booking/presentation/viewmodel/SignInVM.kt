package com.kantek.dancer.booking.presentation.viewmodel

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.extensions.buildMultipart
import com.kantek.dancer.booking.data.extensions.getDeviceID
import com.kantek.dancer.booking.data.helper.network.RequestBodyBuilder
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.data.repo.GetAccountRepo
import com.kantek.dancer.booking.data.repo.SignInRepo
import com.kantek.dancer.booking.domain.model.ui.user.SignInForm
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInVM(
    private val appKeyboard: AppKeyboard,
    private val signInRepo: SignInRepo,
    private val signInGoogleRepo: SignInGoogleRepo,
    private val getAccountRepo: GetAccountRepo
) : AppViewModel() {
    private val _form = MutableStateFlow(SignInForm())
    val formState: StateFlow<SignInForm> = _form
    val loginSuccess = MutableStateFlow(false)

    init {
        getAccount()
    }

    private fun getAccount() = launch(loading, error) {
        val it = getAccountRepo()
        _form.value = SignInForm(account = it.account, password = it.password)
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

    fun loginGoogle(it: GoogleSignInAccount) = launch(loading, error) {
        appKeyboard.hide()
        signInGoogleRepo(it)
        loginSuccess.emit(true)
    }
}

class SignInGoogleRepo(
    private val context: Context,
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi
) {
    suspend operator fun invoke(it: GoogleSignInAccount) {
        val rs = userApi.signInGoogle(
            RequestBodyBuilder()
                .put("first_name", it.givenName)
                .put("last_name", it.familyName)
                .put("email", it.email)
                .put("type", "google")
                .put("mac_address", context.getDeviceID())
                .put("device_token", userLocalSource.getTokenPush())
                .put("device", AppConfig.deviceInfo)
                .put("password", "123456")
                .buildMultipart()
        ).await()
        userLocalSource.saveUserResponse(rs)
    }

}
