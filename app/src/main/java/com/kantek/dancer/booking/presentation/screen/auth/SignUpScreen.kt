package com.kantek.dancer.booking.presentation.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.support.core.extensions.parcelableArrayList
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppSettings
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.extensions.buildMultipart
import com.kantek.dancer.booking.data.extensions.toImagePart
import com.kantek.dancer.booking.data.helper.network.RequestBodyBuilder
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.factory.PhotoFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.SignUpForm
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputPhoneNumber
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppPhotoPickerDialog
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.LegalDisclaimerDialog
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import com.sangcomz.fishbun.FishBun
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: SignUpVM = koinViewModel()) = ScopeProvider {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)
    val formState by viewModel.formState.collectAsState()
    val success by viewModel.onSuccess.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showLegalDisclaimerDialog by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUriPending by remember { mutableStateOf<Uri?>(null) }
    val appSetting = remember { AppSettings(context) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photos: List<Uri> =
                result.data?.parcelableArrayList(FishBun.INTENT_PATH) ?: listOf()
            imageUri = photos.firstOrNull()
            viewModel.updateAvatar(imageUri)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = imageUriPending
            viewModel.updateAvatar(imageUri)
        }
    }

    LaunchedEffect(success) {
        if (success) {
            appNavigator.navigateHome()
        }
    }

    PermissionProvider {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionBarBackAndTitleView(R.string.top_bar_sign_up) { appNavigator.back() }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                SpaceVertical(5.dp)
                Text(
                    text = stringResource(R.string.all_get_started),
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(R.string.reg_enter_your_info),
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
                SpaceVertical(30.dp)
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .background(Colors.Blue227)
                        .clickable { showBottomSheet = true },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri == null) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_avatar_select),
                            contentDescription = "Add Photo",
                            tint = Colors.Primary,
                            modifier = Modifier.size(32.dp)
                        )
                    } else AvatarImage(imageUri.toString(), size = 100.dp)
                }

                SpaceVertical(14.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppInputText(
                        value = formState.firstName,
                        placeHolderRes = R.string.all_first_name,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateFirstName(it) }
                    )
                    AppInputText(
                        value = formState.lastname,
                        placeHolderRes = R.string.all_last_name,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateLastName(it) }
                    )
                }
                SpaceVertical(12.dp)
                AppInputText(
                    value = formState.email,
                    placeHolderRes = R.string.all_email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { viewModel.updateEmail(it) }
                )
                SpaceVertical(12.dp)
                AppInputPhoneNumber(
                    value = formState.phone,
                    placeHolderRes = R.string.all_phone_number,
                    onValueChange = { viewModel.updatePhone(it) }
                )
                SpaceVertical(12.dp)
                AppInputText(
                    value = formState.password,
                    placeHolderRes = R.string.all_password,
                    isPassword = true,
                    maxLength = 6,
                    onValueChange = { viewModel.updatePassword(it) }
                )
                SpaceVertical(20.dp)
                AppButton(R.string.all_register) { viewModel.signUp() }
                SpaceVertical(10.dp)
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { showLegalDisclaimerDialog = true }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Colors.Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "i",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    SpaceHorizontal(8.dp)

                    Text(
                        text = stringResource(R.string.reg_legal_disclaimer),
                        color = Colors.Primary,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                }
                Column(Modifier.weight(1f)) { }
                SpaceVertical(20.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.auth_already_have_an_account),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    SpaceHorizontal(30.dp)
                    AppButton(
                        nameRes = R.string.all_sign_in,
                        backgroundColor = Colors.Blue241,
                        textColor = Colors.Primary,
                        modifier = Modifier.height(55.dp)
                    ) {
                        appNavigator.back()
                    }
                }
            }

            if (showBottomSheet) {
                AppPhotoPickerDialog(
                    sheetState,
                    onCameraClick = {
                        accessCapture {
                            appSetting.openCameraForImage(cameraLauncher) {
                                imageUriPending = it
                            }
                        }
                    },
                    onGalleryClick = {
                        accessReadImage { appSetting.openGalleryForImage(galleryLauncher) }
                    }, onDismiss = { showBottomSheet = false })
            }

            if (showLegalDisclaimerDialog) {
                LegalDisclaimerDialog(
                    formState.hasAgree,
                    onAgree = {
                        showLegalDisclaimerDialog = false
                        viewModel.updateLegalDisclaimer(true)
                    },
                    onDismiss = {
                        showLegalDisclaimerDialog = false
                    }
                )
            }
        }
    }
}

class SignUpVM(
    private val appKeyboard: AppKeyboard,
    private val signUpRepo: SignUpRepo
) : AppViewModel() {

    private val _form = MutableStateFlow(SignUpForm())
    val formState: StateFlow<SignUpForm> = _form

    val onSuccess = MutableStateFlow(false)

    fun updateFirstName(it: String) {
        if (_form.value.firstName != it)
            _form.value = _form.value.copy(firstName = it)
    }

    fun updateLastName(it: String) {
        if (_form.value.lastname != it)
            _form.value = _form.value.copy(lastname = it)
    }

    fun updateEmail(it: String) {
        if (_form.value.email != it)
            _form.value = _form.value.copy(email = it)
    }

    fun updatePhone(it: String) {
        if (_form.value.phone != it)
            _form.value = _form.value.copy(phone = it)
    }

    fun updatePassword(it: String) {
        if (_form.value.password != it)
            _form.value = _form.value.copy(password = it)
    }

    fun updateAvatar(it: Uri?) {
        if (_form.value.avatarUri != it)
            _form.value = _form.value.copy(avatarUri = it)
    }

    fun updateLegalDisclaimer(it: Boolean) {
        if (_form.value.hasAgree != it)
            _form.value = _form.value.copy(hasAgree = it)
    }

    fun signUp() = launch(loading, error) {
        appKeyboard.hide()
        _form.value.run {
            valid()
            signUpRepo(this)
            onSuccess.value = true
        }
    }
}

class SignUpRepo(
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi,
    private val photoFactory: PhotoFactory
) {
    suspend operator fun invoke(form: SignUpForm) {
        var avatarPart: MultipartBody.Part? = null
        if (form.avatarUri != null)
            avatarPart = photoFactory.resizeIfNeed(form.avatarUri!!)?.toImagePart("avatar")

        userLocalSource.saveUserResponse(
            userApi.signUp(
                RequestBodyBuilder()
                    .put("first_name", form.firstName)
                    .put("last_name", form.lastname)
                    .put("phone", form.phone)
                    .put("email", form.email)
                    .put("password", form.password)
                    .put("device_token", userLocalSource.getTokenPush())
                    .put("device", form.device_info)
                    .put("mac_address", form.macAddress)
                    .buildMultipart(), avatarPart
            ).await()
        )
    }
}
