package com.kantek.dancer.booking.presentation.screen.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.LawyerApi
import com.kantek.dancer.booking.domain.extension.toObject
import com.kantek.dancer.booking.domain.model.form.ReviewForm
import com.kantek.dancer.booking.domain.model.response.BookingDTO
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.ILawyerDetail
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.screen.lawyer.GetLawyerDetailRepo
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.LawyerInfo
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateReviewScreen(
    dataJson: String = "",//BookingDTO::class
    viewModel: CreateReviewVM = koinViewModel()
) = ScopeProvider(Scopes.MyCase) {

    val appNavigator = use<AppNavigator>(Scopes.App)
    val formState by viewModel.formState.collectAsState()
    val detail by viewModel.details.collectAsState()
    val onSuccess by viewModel.onSuccess.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setData(dataJson)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Gray249),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.all_leave_review) { appNavigator.back() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {

                    if (detail != null)
                        LawyerInfo(it = detail!!,
                            rating = formState.rating,
                            onRatingChanged = { viewModel.updateRating(it) })

                    SpaceVertical(20.dp)
                    AppInputText(value = formState.review_text,
                        singleLine = false,
                        maxLines = 6,
                        modifier = Modifier.height(150.dp),
                        placeHolderRes = R.string.review_write,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = {
                            viewModel.updateContents(it)
                        })

                    SpaceVertical(30.dp)
                    AppButton(
                        nameRes = R.string.all_submit,
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth()
                    ) {
                        viewModel.submit()
                    }
                }
            }
        }
        if (onSuccess)
            AppNotificationDialog(stringResource( R.string.review_success)) {
                appNavigator.back()
            }
    }
}


class CreateReviewVM(
    private val appEvent: AppEvent,
    private val getLawyerDetailRepo: GetLawyerDetailRepo,
    private val createReviewRepo: CreateReviewRepo
) : AppViewModel() {
    val details = MutableStateFlow<ILawyerDetail?>(null)
    private val _form = MutableStateFlow(ReviewForm())
    val formState: StateFlow<ReviewForm> = _form
    val onSuccess = MutableStateFlow(false)

    fun setData(dataJson: String) = launch(loading, error) {
        val bookingDTO = try {
            dataJson.toObject<BookingDTO>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        _form.value = _form.value.copy(contact_request_id = bookingDTO?.id)
        details.emit(getLawyerDetailRepo(dataJson))
    }

    fun updateRating(it: Float) {
        if (_form.value.rating != it) {
            _form.value = _form.value.copy(rating = it)
        }
    }

    fun updateContents(it: String) {
        if (_form.value.review_text != it) {
            _form.value = _form.value.copy(review_text = it)
        }
    }

    fun submit() = launch(loading, error) {
        _form.value.valid()
        createReviewRepo(_form.value)
        appEvent.onRefreshMyCases.emit(true)
        onSuccess.emit(true)
    }

}

class CreateReviewRepo(private val lawyerApi: LawyerApi) {
    suspend operator fun invoke(value: ReviewForm) {
        lawyerApi.createReview(value).await()
    }

}
