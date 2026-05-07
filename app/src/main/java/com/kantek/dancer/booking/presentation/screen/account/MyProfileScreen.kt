package com.kantek.dancer.booking.presentation.screen.account

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import com.kantek.dancer.booking.domain.model.ui.user.ProfileForm
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.AppPopup
import com.kantek.dancer.booking.presentation.provider.PermissionProvider
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppPhotoPickerDialog
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(viewModel: MyProfileVM = koinViewModel()) = ScopeProvider(Scopes.Account) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>()
    val formState by viewModel.formState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var imageUriPending by remember { mutableStateOf<Uri?>(null) }
    val appSetting = remember { AppSettings(context) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        viewModel.updateAvatar(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.updateAvatar(imageUriPending)
        }
    }

    PermissionProvider {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionBarBackAndTitleView(R.string.top_bar_my_profile) { appNavigator.back() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                ) {
                    SpaceVertical(30.dp)
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { showBottomSheet = true },
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(132.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(Colors.Primary, Colors.Primary.copy(alpha = 0.2f))
                                    )
                                )
                                .padding(2.dp)
                        ) {
                            if (formState.avatarUri == null) {
                                AvatarImage(formState.avatarPath, size = 128.dp)
                            } else AvatarImage(formState.avatarUri.toString(), size = 128.dp)
                        }

                        Box(
                            modifier = Modifier
                                .offset(x = (-4).dp, y = (-2).dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Colors.Primary)
                                .border(3.dp, Colors.Dark120812, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit avatar",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Text(
                        text = stringResource(R.string.account_change_photo).uppercase(),
                        color = Colors.Primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                            .clickable { showBottomSheet = true }
                    )

                    SpaceVertical(50.dp)
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
                        readOnly = true,
                        placeHolderRes = R.string.all_email,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = { viewModel.updateEmail(it) }
                    )
//                    SpaceVertical(12.dp)
//                    AppInputPhoneNumber(
//                        value = formState.phone,
//                        lightBackground = false,
//                        placeHolderRes = R.string.all_phone_number,
//                        onValueChange = { viewModel.updatePhone(it) }
//                    )
                    SpaceVertical(50.dp)
                    Button(
                        onClick = { viewModel.save(context) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Colors.Primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SAVE CHANGES",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                letterSpacing = 3.sp,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "Save profile",
                                tint = Color.White
                            )
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
                            appSetting.openGalleryForImage(photoPickerLauncher)
                        },
                        onDismiss = { showBottomSheet = false }
                    )
                }
            }
        }
    }
}

class MyProfileVM(
    private val appKeyboard: AppKeyboard,
    private val updateProfileRepo: UpdateProfileRepo,
    private val appPopup: AppPopup
) : AppViewModel() {

    private val _form = MutableStateFlow(ProfileForm())
    val formState: StateFlow<ProfileForm> = _form

    init {
        launch(null, error) {
            userLive.collect {
                updateFom(it)
            }
        }
    }

    private fun updateFom(it: IUser?) {
        if (it != null) {
            _form.value = _form.value.copy(avatarPath = it.avatarURL)
            updateAvatar(null)
            updateFirstName(it.firstName)
            updateLastName(it.lastName)
            updateEmail(it.email)
            updatePhone(it.phoneNumber)
        }
    }

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

    fun updateAvatar(it: Uri?) {
        _form.value = _form.value.copy(avatarUri = it)
    }

    fun save(context: Context) = launch(loading, error) {
        appKeyboard.hide()
        _form.value.apply {
            valid()
            updateProfileRepo(this)
            loading.stop()
            appPopup.show(context.getString(R.string.msg_update_profile_success))
        }
    }

}

class UpdateProfileRepo(
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi,
    private val photoFactory: PhotoFactory
) {
    suspend operator fun invoke(form: ProfileForm) {
        var avatarPart: MultipartBody.Part? = null
        if (form.avatarUri != null)
            avatarPart = photoFactory.resizeIfNeed(form.avatarUri!!)?.toImagePart("avatar")

        userLocalSource.saveUser(
            userApi.updateProfile(
                RequestBodyBuilder()
                    .put("firstName", form.firstName)
                    .put("lastName", form.lastname)
                    .buildMultipart(), avatarPart
            ).await()
        )
    }

}
