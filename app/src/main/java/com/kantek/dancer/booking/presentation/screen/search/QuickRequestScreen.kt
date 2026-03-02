package com.kantek.dancer.booking.presentation.screen.search

import android.content.Context
import android.support.core.extensions.safe
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.libraries.places.api.model.Place
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.repo.LanguageRepo
import com.kantek.dancer.booking.domain.extension.toObject
import com.kantek.dancer.booking.domain.factory.LawyerFactory
import com.kantek.dancer.booking.domain.model.form.BookingForm
import com.kantek.dancer.booking.domain.model.response.lawyer.LawyerDTO
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.ILanguage
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppDropdown
import com.kantek.dancer.booking.presentation.widget.AppInputPhoneNumber
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppNextButton
import com.kantek.dancer.booking.presentation.widget.AppPlaceAutocomplete
import com.kantek.dancer.booking.presentation.widget.AvatarImage
import com.kantek.dancer.booking.presentation.widget.BookingSuccessDialog
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuickRequestScreen(
    dataJson: String = "",//LawyerDTO::class
    viewModel: QuickRequestVM = koinViewModel()
) = ScopeProvider(Scopes.Search) {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)

    val formState by viewModel.formState.collectAsState()
    val languages by viewModel.languages.collectAsState()
    val refreshData by remember { mutableStateOf(viewModel.getCurrentLanguage()) }
    LaunchedEffect(refreshData) { viewModel.onRefresh(context) }

    val hasEditablePersonalInfo by viewModel.hasEditablePersonalInfo.collectAsState()
    val onSuccess by viewModel.onSuccess.collectAsState()
    val showDialog = remember { mutableStateOf(-1) }
    LaunchedEffect(onSuccess) {
        showDialog.value = onSuccess
    }

    val lawyerSelected by viewModel.requestWithLawyer.collectAsState()
    val languagesOfLawyerSelected by viewModel.languagesOfLawyerSelected.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setData(context, dataJson)
    }

    fun openAuth() {
        appNavigator.navigateSignIn()
    }

    fun dismissDialog() {
        viewModel.clearBooking()
        showDialog.value = -1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_request_consultation) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.find_consultation_info),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (lawyerSelected != null) {
                SpaceVertical(5.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .background(Colors.Gray238, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    AvatarImage(url = lawyerSelected?.avatar_url, size = 40.dp)

                    SpaceHorizontal(12.dp)

                    Text(
                        text = lawyerSelected?.name.safe(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                SpaceVertical(7.dp)
                AppButton(
                    R.string.all_change_lawyer,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    textColor = Colors.Primary,
                    backgroundColor = Colors.Blue227,
                    fontSize = 14,
                ) { appNavigator.navigateChangeLawyer() }
                SpaceVertical(7.dp)
            }

            SpaceVertical(5.dp)
            AppPlaceAutocomplete(value = formState.address.safe(),
                placeHolderRes = R.string.all_address,
                onPlaceSelected = { viewModel.updateAddress(it) })

            SpaceVertical(14.dp)
            AppInputText(value = formState.description,
                singleLine = false,
                maxLines = 6,
                modifier = Modifier.height(150.dp),
                placeHolderRes = R.string.find_des_your_issue,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text
                ),
                onValueChange = { viewModel.updateDescription(it) })

            if (languagesOfLawyerSelected == null) {
                SpaceVertical(14.dp)
                AppDropdown(
                    valueSelected = formState.languageSelected,
                    placeHolderRes = R.string.find_consultation_language,
                    items = languages,
                ) { viewModel.updateLanguageSelected(it) }
            } else {
                SpaceVertical(14.dp)
                AppInputText(
                    value = languagesOfLawyerSelected.safe(),
                    readOnly = true,
                    placeHolderRes = R.string.find_consultation_language
                )
            }

            SpaceVertical(20.dp)
            Text(
                text = stringResource(R.string.all_personal_info),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            SpaceVertical(5.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppInputText(value = formState.first_name.safe(),
                    readOnly = !hasEditablePersonalInfo,
                    placeHolderRes = R.string.all_first_name,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { viewModel.updateFirstName(it) })
                AppInputText(value = formState.last_name.safe(),
                    readOnly = !hasEditablePersonalInfo,
                    placeHolderRes = R.string.all_last_name,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { viewModel.updateLastName(it) })
            }
            SpaceVertical(14.dp)
            AppInputText(value = formState.customer_email.safe(),
                readOnly = !hasEditablePersonalInfo,
                placeHolderRes = R.string.all_email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { viewModel.updateEmail(it) })
            SpaceVertical(14.dp)
            AppInputPhoneNumber(value = formState.customer_phone.safe(),
                readOnly = !hasEditablePersonalInfo,
                placeHolderRes = R.string.all_phone_number,
                onValueChange = { viewModel.updatePhone(it) })
            SpaceVertical(30.dp)
            AppNextButton(
                nameRes = R.string.all_send_request,
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
            ) {
                viewModel.submit()
            }
        }
        if (showDialog.value == 0) BookingSuccessDialog(title = stringResource(R.string.booking_request_submitted),
            stringResource(R.string.booking_without_login_success),
            textConfirm = stringResource(R.string.all_sign_in),
            onConfirm = {
                dismissDialog()
                openAuth()
            },
            onDismiss = { dismissDialog() })
        else if (showDialog.value != -1) {
            val bookingID = showDialog.value
            BookingSuccessDialog(title = stringResource(R.string.booking_request_submitted),
                stringResource(R.string.booking_success),
                textConfirm = stringResource(R.string.all_view_detail),
                onConfirm = {
                    dismissDialog()
                    appNavigator.navigateDetailCase(bookingID)
                },
                onDismiss = { dismissDialog() })
        }
    }
}

class QuickRequestVM(
    private val languageRepo: LanguageRepo,
    private val bookingCreateRepo: BookingCreateRepo,
    private val lawyerFactory: LawyerFactory
) : AppViewModel() {
    val languages = MutableStateFlow<List<ILanguage>>(emptyList())
    private val _form = MutableStateFlow(BookingForm())
    val formState: StateFlow<BookingForm> = _form
    val hasEditablePersonalInfo = MutableStateFlow(false)
    val onSuccess = bookingCreateRepo.result
    val requestWithLawyer = MutableStateFlow<LawyerDTO?>(null)
    val languagesOfLawyerSelected = MutableStateFlow<String?>(null)

    fun setData(context: Context, dataJson: String) = launch(loading, error) {
        val lawyer = try {
            dataJson.toObject<LawyerDTO>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        requestWithLawyer.emit(lawyer)
        languagesOfLawyerSelected.emit(
            lawyerFactory.formatLanguageDisplay(
                context,
                lawyer?.languages
            )
        )
        _form.value.partner_id = lawyer?.id
    }

    init {
        launch(null, error) {
            userLive.collect {
                hasEditablePersonalInfo.emit(it == null)
                updateUser(it)
            }
        }
    }

    private fun updateUser(it: IUser?) {
        updateFirstName(it?.firstName)
        updateLastName(it?.lastName)
        updateEmail(it?.email)
        updatePhone(it?.phoneNumber)
    }

    fun updateFirstName(it: String?) {
        if (_form.value.first_name != it) {
            _form.value = _form.value.copy(first_name = it)
        }
    }

    fun updateLastName(it: String?) {
        if (_form.value.last_name != it) {
            _form.value = _form.value.copy(last_name = it)
        }
    }

    fun updatePhone(it: String?) {
        if (_form.value.customer_phone != it) {
            _form.value = _form.value.copy(customer_phone = it)
        }
    }

    fun updateEmail(it: String?) {
        if (_form.value.customer_email != it) {
            _form.value = _form.value.copy(customer_email = it)
        }
    }

    private fun fetchLanguages(context: Context) = launch(loading, error) {
        val rs = languageRepo.fetchAll(context)
        updateLanguageSelected(rs[0])
        languages.emit(rs)
    }

    fun onRefresh(context: Context) {
        if (currentLanguageBackup != getCurrentLanguage()) {
            currentLanguageBackup = getCurrentLanguage()
            fetchLanguages(context)
        }
    }

    fun updateLanguageSelected(it: ILanguage) {
        if (_form.value.languageSelected != it) {
            _form.value =
                _form.value.copy(languageSelected = it, language_skill = listOf(it).map { it.id })
        }
    }

    fun updateDescription(it: String) {
        if (_form.value.description != it) {
            _form.value = _form.value.copy(description = it)
        }
    }

    fun updateAddress(place: Place) {
        if (_form.value.address != place.address) {
            _form.value = _form.value.copy(address = place.address.safe())
        }
    }

    fun submit() = launch(loading, error) {
        _form.value.valid()
        bookingCreateRepo(_form.value)
    }

    fun clearBooking() {
        _form.value = _form.value.copy(description = "", address = "")
        onSuccess.value = -1
    }

}

class BookingCreateRepo(
    private val appEvent: AppEvent,
    private val bookingApi: BookingApi,
    private val userLocalSource: UserLocalSource
) {
    val result = MutableStateFlow(-1)
    suspend operator fun invoke(form: BookingForm) {
        if (userLocalSource.isLogin()) {
            val rs = bookingApi.create(form).await()
            appEvent.apply {
                onRefreshMyCases.emit(true)
                onRefreshNotification.emit(true)
            }
            result.emit(rs.id)
        } else {
            bookingApi.createWithoutAuth(form).await()
            result.emit(0)
        }
    }

}
