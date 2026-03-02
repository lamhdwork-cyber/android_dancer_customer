package com.kantek.dancer.booking.presentation.screen.account

import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputPhoneNumber
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppPhotoPickerDialog
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import com.sangcomz.fishbun.FishBun
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(viewModel: MyProfileVM = koinViewModel()) = ScopeProvider(Scopes.Account) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)
    val formState by viewModel.formState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var imageUriPending by remember { mutableStateOf<Uri?>(null) }
    val appSetting = remember { AppSettings(context) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photos: List<Uri> =
                result.data?.parcelableArrayList(FishBun.INTENT_PATH) ?: listOf()
            viewModel.updateAvatar(photos.firstOrNull())
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.updateAvatar(imageUriPending)
        }
    }

    PermissionProvider {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionBarBackAndTitleView(R.string.top_bar_my_profile) { appNavigator.back() }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
            ) {
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
                    if (formState.avatarUri == null) {
                        AvatarImage(formState.avatarPath, size = 100.dp)
                    } else AvatarImage(formState.avatarUri.toString(), size = 100.dp)
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }

                SpaceVertical(20.dp)
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
                SpaceVertical(20.dp)
                AppButton(R.string.all_save) { viewModel.save(context) }
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
        }
    }
}

class MyProfileVM(
    private val appKeyboard: AppKeyboard,
    private val updateProfileRepo: UpdateProfileRepo,
    private val appPopup: AppPopup
) : AppViewModel() {

    init {
        launch(null, error) {
            userLive.collect {
                updateFom(it)
            }
        }
    }

    private val _form = MutableStateFlow(ProfileForm())
    val formState: StateFlow<ProfileForm> = _form

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
            _form.value = ProfileForm()
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
                    .put("first_name", form.firstName)
                    .put("last_name", form.lastname)
                    .put("phone", form.phone)
                    .put("email", form.email)
                    .buildMultipart(), avatarPart
            ).await()
        )
    }

}
