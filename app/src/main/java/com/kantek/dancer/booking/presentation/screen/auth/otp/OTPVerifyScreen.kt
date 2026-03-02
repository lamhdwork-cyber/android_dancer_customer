package com.kantek.dancer.booking.presentation.screen.auth.otp

import android.content.Context
import android.support.core.event.ErrorEvent
import android.support.core.event.ErrorFlow
import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.extensions.getDeviceID
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.screen.auth.forgot.RequestOTPRepo
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.OtpVerificationScreen
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun OTPVerifyScreen(
    email: String,
    viewModel: OTPVerifyVM = koinViewModel()
) = ScopeProvider {
    val appNavigator = use<AppNavigator>(Scopes.App)
    val lifecycleOwner = LocalLifecycleOwner.current

    var isOtpInvalid by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val isLoading by viewModel.loadingCustom.isLoading().collectAsState()
    viewModel.errorCustom.observe(lifecycleOwner) {
        isOtpInvalid = true
        errorMessage = it.message
    }
    val emailVerified by viewModel.emailVerified.collectAsState()

    LaunchedEffect(emailVerified) {
        if (emailVerified.isNotEmpty()) {
            appNavigator.navigateResetPassword(emailVerified)
            viewModel.emailVerified.emit("")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_otp_verify) { appNavigator.back() }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.otp_des_1),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                text = email,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            SpaceVertical(10.dp)
            Text(
                text = stringResource(R.string.otp_des_2),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            OtpVerificationScreen(
                isLoading = isLoading,
                isOtpInvalid = isOtpInvalid,
                errorMessage = errorMessage,
                onOtpComplete = { otp ->
                    isOtpInvalid = false
                    errorMessage = null
                    viewModel.verifyOTP(email, otp)
                },
                onResend = {
                    isOtpInvalid = false
                    errorMessage = null
                    viewModel.setResending(email)
                }
            )
        }
    }

}

class OTPVerifyVM(
    private val verifyOTPRepo: VerifyOTPRepo,
    private val requestOTPRepo: RequestOTPRepo
) : AppViewModel() {
    val emailVerified = verifyOTPRepo.result

    val errorCustom: ErrorEvent = ErrorFlow()
    val loadingCustom: LoadingEvent = LoadingFlow()

    fun verifyOTP(email: String, otp: String) = launch(loadingCustom, errorCustom) {
        verifyOTPRepo(email, otp)
    }

    fun setResending(email: String) = launch(loading, error) {
        requestOTPRepo(email, AppConfig.OTPType.FORGOT_PASSWORD)
    }
}

class VerifyOTPRepo(
    private val context: Context,
    private val userApi: UserApi
) {
    val result = MutableStateFlow("")

    suspend operator fun invoke(email: String, otp: String) {
        userApi.verifyOTP(
            email,
            otp,
            context.getDeviceID()
        ).await()

        result.emit(email)
    }
}