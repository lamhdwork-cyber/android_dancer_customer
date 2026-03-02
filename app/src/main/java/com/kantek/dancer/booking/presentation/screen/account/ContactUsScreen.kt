package com.kantek.dancer.booking.presentation.screen.account

import android.content.Context
import android.support.core.extensions.safe
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppSettings
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.remote.api.ConfigApi
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.factory.ConfigFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.config.ContactForm
import com.kantek.dancer.booking.domain.model.ui.config.ISetting
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputPhoneNumber
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.CallPhoneDialog
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactUsScreen(viewModel: ContactUsVM = koinViewModel()) = ScopeProvider(Scopes.Account) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    val appSetting = remember { AppSettings(context) }
    var hasShowCallDialog by remember { mutableStateOf(false) }
    val setting by viewModel.setting.collectAsState()
    val formState by viewModel.formState.collectAsState()

    PermissionProvider {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionBarBackAndTitleView(R.string.all_contact_us) { appNavigator.back() }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
            ) {
                if (setting != null) {
                    SpaceVertical(5.dp)
                    Text(
                        text = stringResource(R.string.all_contact_info),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(R.string.contact_us_des),
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                    SpaceVertical(14.dp)
                    Row(
                        modifier = Modifier
                            .clickable {
                            }
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_address),
                            contentDescription = "address",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = setting!!.address,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )
                    Row(
                        modifier = Modifier
                            .clickable {
                                accessCallPhone { hasShowCallDialog = true }
                            }
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_phone),
                            contentDescription = "phone",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = setting!!.phoneDisplay,
                            color = Colors.Primary,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Colors.Gray238
                    )

                    Row(
                        modifier = Modifier
                            .clickable { appSetting.sendEmail(setting!!.email) }
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_mail),
                            contentDescription = "email",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = setting!!.email,
                            color = Colors.Primary,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                SpaceVertical(20.dp)
                Text(
                    text = stringResource(R.string.all_message),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppInputText(
                        value = formState.firstName.safe(),
                        placeHolderRes = R.string.all_first_name,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateFirstName(it) }
                    )
                    AppInputText(
                        value = formState.lastname.safe(),
                        placeHolderRes = R.string.all_last_name,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateLastName(it) }
                    )
                }
                SpaceVertical(14.dp)
                AppInputText(
                    value = formState.email.safe(),
                    placeHolderRes = R.string.all_email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { viewModel.updateEmail(it) }
                )
                SpaceVertical(14.dp)
                AppInputPhoneNumber(
                    value = formState.phone.safe(),
                    placeHolderRes = R.string.all_phone_number,
                    onValueChange = { viewModel.updatePhone(it) }
                )
                SpaceVertical(14.dp)
                AppInputText(
                    value = formState.content,
                    singleLine = false,
                    maxLines = 6,
                    modifier = Modifier.height(150.dp),
                    placeHolderRes = R.string.contact_us_content,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { viewModel.updateContent(it) }
                )
                SpaceVertical(30.dp)
                AppButton(nameRes = R.string.all_send) {
                    viewModel.submit(context)
                }

            }

            if (hasShowCallDialog) {
                CallPhoneDialog(
                    phoneNumber = setting!!.phoneDisplay,
                    onConfirm = {
                        hasShowCallDialog = false
                        appSetting.call(setting!!.phone)
                    }, onDismiss = {
                        hasShowCallDialog = false
                    }
                )
            }
        }
    }
}

class ContactUsVM(
    private val fetchSettingRepo: FetchSettingRepo,
    private val contactUsRepo: ContactUsRepo,
    private val appPopup: AppPopup
) : AppViewModel() {
    val setting = fetchSettingRepo.result
    val formState = MutableStateFlow(ContactForm())

    init {
        fetchSetting()
    }

    private fun fetchSetting() = launch(loading, error) {
        fetchSettingRepo()
    }

    fun updateFirstName(it: String) {
        if (formState.value.firstName != it) {
            formState.value = formState.value.copy(firstName = it)
        }
    }

    fun updateLastName(it: String) {
        if (formState.value.lastname != it) {
            formState.value = formState.value.copy(lastname = it)
        }
    }

    fun updateEmail(it: String) {
        if (formState.value.email != it) {
            formState.value = formState.value.copy(email = it)
        }
    }

    fun updatePhone(it: String) {
        if (formState.value.phone != it) {
            formState.value = formState.value.copy(phone = it)
        }
    }

    fun updateContent(it: String) {
        if (formState.value.content != it) {
            formState.value = formState.value.copy(content = it)
        }
    }

    fun submit(context: Context) = launch(loading, error) {
        formState.value.run {
            valid()
            contactUsRepo(this)
            loading.stop()
            appPopup.show(context.getString(R.string.msg_contact_us_success))
        }
    }

}

class ContactUsRepo(private val userApi: UserApi) {

    suspend operator fun invoke(form: ContactForm) {
        userApi.contactUs(form).await()
    }

}

class FetchSettingRepo(
    private val configApi: ConfigApi,
    private val configFactory: ConfigFactory,
) {
    val result = MutableStateFlow<ISetting?>(null)

    suspend operator fun invoke() {
        result.emit(configFactory.createSetting(configApi.settings().await()))
    }

}
