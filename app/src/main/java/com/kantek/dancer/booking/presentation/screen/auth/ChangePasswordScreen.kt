package com.kantek.dancer.booking.presentation.screen.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.ChangePasswordForm
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
fun ChangePasswordScreen(viewModel: ChangePasswordVM = koinViewModel()) = ScopeProvider {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    val formState by viewModel.formState.collectAsState()
    val onBack by viewModel.onSuccess.collectAsState()

    LaunchedEffect(onBack) {
        if (onBack)
            appNavigator.back()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_change_password) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SpaceVertical(16.dp)
            AppInputText(
                value = formState.currentPassword,
                placeHolderRes = R.string.all_current_password,
                isPassword = true,
                maxLength = 6,
                onValueChange = { viewModel.updateCurrentPassword(it) }
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
                value = formState.confirmNewPassword,
                placeHolderRes = R.string.all_confirm_new_password,
                isPassword = true,
                maxLength = 6,
                onValueChange = { viewModel.updateConfirmPassword(it) }
            )

            SpaceVertical(30.dp)
            AppButton(R.string.all_change) {
                viewModel.submit(context)
            }
        }
    }
}

class ChangePasswordVM(
    private val changePasswordRepo: ChangePasswordRepo,
    private val appPopup: AppPopup
) : AppViewModel() {

    val formState = MutableStateFlow(ChangePasswordForm())
    val onSuccess = MutableStateFlow(false)

    fun updateCurrentPassword(it: String) {
        if (formState.value.currentPassword != it)
            formState.value = formState.value.copy(currentPassword = it)
    }

    fun updateNewPassword(it: String) {
        if (formState.value.newPassword != it)
            formState.value = formState.value.copy(newPassword = it)
    }

    fun updateConfirmPassword(it: String) {
        if (formState.value.confirmNewPassword != it)
            formState.value = formState.value.copy(confirmNewPassword = it)
    }

    fun submit(context: Context) = launch(loading, error) {
        formState.value.valid()
        changePasswordRepo(formState.value)
        loading.stop()
        appPopup.show(context.getString(R.string.msg_change_password_success))
        onSuccess.emit(true)
    }
}

class ChangePasswordRepo(
    private val userApi: UserApi
) {

    suspend operator fun invoke(formState: ChangePasswordForm) {
        userApi.changePassword(formState).await()
    }

}
