package com.kantek.dancer.booking.presentation.screen.auth.forgot

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.ResetPasswordForm
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNewPwScreen(
    email: String,
    viewModel: RetPasswordVM = koinViewModel()
) = ScopeProvider {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    val formState by viewModel.formState.collectAsState()
    val onSuccess by viewModel.success.collectAsState()

    LaunchedEffect(onSuccess) {
        if (onSuccess) {
            appNavigator.navigateSignIn(true)
            viewModel.success.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_reset_pw) { appNavigator.back() }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.reset_pw_des),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            SpaceVertical(16.dp)
            AppInputText(
                value = formState.newPassword,
                placeHolderRes = R.string.all_new_password,
                isPassword = true,
                maxLength = 6,
                onValueChange = { viewModel.updateNewPassword(it) }
            )

            SpaceVertical(16.dp)
            AppInputText(
                value = formState.passwordConfirm,
                placeHolderRes = R.string.all_confirm_new_password,
                isPassword = true,
                maxLength = 6,
                onValueChange = { viewModel.updateConfirmPassword(it) }
            )

            SpaceVertical(30.dp)
            AppButton(R.string.all_contiue) {
                viewModel.submit(context, email)
            }
        }
    }
}

class RetPasswordVM(
    private val resetPasswordRepo: ResetPasswordRepo,
    private val appPopup: AppPopup
) : AppViewModel() {
    val formState = MutableStateFlow(ResetPasswordForm())
    val success = MutableStateFlow(false)

    fun submit(
        context: Context,
        email: String
    ) = launch(loading, error) {
        formState.value.run {
            formState.value.valid(email)
            resetPasswordRepo(this)
            loading.stop()
            appPopup.show(context.getString(R.string.reset_password_success))
            success.emit(true)
        }
    }

    fun updateNewPassword(it: String) {
        if (formState.value.newPassword != it)
            formState.value = formState.value.copy(newPassword = it)
    }

    fun updateConfirmPassword(it: String) {
        if (formState.value.passwordConfirm != it)
            formState.value = formState.value.copy(passwordConfirm = it)
    }
}

class ResetPasswordRepo(private val userApi: UserApi) {

    suspend operator fun invoke(form: ResetPasswordForm) {
        form.email = form.email
        userApi.resetPassword(form).await()
    }

}
