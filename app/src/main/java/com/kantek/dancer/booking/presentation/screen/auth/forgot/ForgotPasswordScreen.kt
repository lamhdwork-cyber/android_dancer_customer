package com.kantek.dancer.booking.presentation.screen.auth.forgot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.extension.isEmail
import com.kantek.dancer.booking.domain.extension.resourceError
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordVM = koinViewModel()) = ScopeProvider {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val emailState by viewModel.email.collectAsState()
    val onSuccess by viewModel.success.collectAsState("")
    val onEmailVerified by viewModel.otpVerify.collectAsState()

    LaunchedEffect(onEmailVerified) {
        if(onEmailVerified.isNotEmpty()){
            appNavigator.navigateOTPVerify(onEmailVerified)
            viewModel.otpVerify.emit("")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_forgot_pw) { appNavigator.back() }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.forgot_pw_des),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            SpaceVertical(30.dp)
            AppInputText(
                value = emailState,
                placeHolderRes = R.string.all_email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { viewModel.updateEmail(it) }
            )
            SpaceVertical(20.dp)
            AppButton(R.string.all_next) { viewModel.setEmail() }
        }
        if (onSuccess.isNotEmpty())
            AppNotificationDialog(onSuccess) { }
    }
}

class ForgotPasswordVM(
    private val appKeyboard: AppKeyboard,
    private val forgotPwRepo: ForgotPasswordRepo,
    private val requestOTPRepo: RequestOTPRepo
) : AppViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    val success = forgotPwRepo.result

    val otpVerify = MutableStateFlow("")

    fun updateEmail(it: String) {
        if (_email.value != it)
            _email.value = it
    }

    fun setEmail() = launch(loading, error) {
        appKeyboard.hide()
        val email = _email.value
        if (email.isBlank())
            resourceError(R.string.error_blank_email)
        if (!email.isEmail())
            resourceError(R.string.error_valid_email)
        requestOTPRepo(email, AppConfig.OTPType.FORGOT_PASSWORD)
        otpVerify.emit(email)
    }

}


class ForgotPasswordRepo(private val userApi: UserApi) {
    val result = MutableSharedFlow<String>(0)

    suspend operator fun invoke(email: String) {
        result.emit(userApi.forgotPassword(email).await())
    }
}

class RequestOTPRepo(
    private val userApi: UserApi
) {

    suspend operator fun invoke(email: String, type: String) {
        userApi.requestOTP(email, type).await()
    }
}

