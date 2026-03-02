package com.kantek.dancer.booking.presentation.screen.cases

import android.support.core.extensions.safe
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppNotifications
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.factory.BookingFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.booking.IBookingDetail
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.CancellationReasonDialog
import com.kantek.dancer.booking.presentation.widget.LawyerInfo
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailCaseScreen(
    bookingID: Int = -1,
    viewModel: DetailCasesVM = koinViewModel()
) = ScopeProvider(Scopes.MyCase) {

    val appNavigator = use<AppNavigator>(Scopes.App)
    val detail by viewModel.details.collectAsState()
    val onBack by viewModel.onBack.collectAsState()

    //Actions
    var hasShowRequest by remember { mutableStateOf(false) }
    var hasShowCancel by remember { mutableStateOf(false) }

    LaunchedEffect(bookingID) {
        if (bookingID != -1 && detail == null)
            viewModel.fetchDetails()
    }
    LaunchedEffect(onBack) {
        if (onBack) {
            appNavigator.back()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.requestID = bookingID
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Gray249),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_detail_case) { appNavigator.back() }
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
                if (detail != null) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        // Case ID and Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.case_id_s, detail!!.id),
                                fontSize = 14.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(detail!!.colorStatus)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = detail!!.statusDisplay,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        SpaceVertical(10.dp)

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )

                        SpaceVertical(10.dp)

                        Text(
                            text = stringResource(R.string.all_address),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = detail!!.address,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        SpaceVertical(12.dp)

                        Text(
                            text = stringResource(R.string.find_consultation_language),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = detail!!.language,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        SpaceVertical(12.dp)

                        Text(
                            text = stringResource(R.string.all_created_on),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = detail!!.datetime,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        if (detail!!.hasCancel && !detail?.reason.isNullOrEmpty()) {
                            SpaceVertical(12.dp)

                            Text(
                                text = stringResource(R.string.all_cancel_reason),
                                fontSize = 12.sp,
                                color = Colors.Blue95
                            )

                            Text(
                                text = detail!!.reason,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }

                        SpaceVertical(12.dp)

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )

                        SpaceVertical(12.dp)

                        // Description Title
                        Text(
                            stringResource(R.string.find_issue_des),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Description Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Colors.Blue247, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(text = detail!!.description, fontSize = 14.sp)
                        }

                        SpaceVertical(14.dp)

                        // Personal Info
                        Text(
                            stringResource(R.string.all_personal_info),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        SpaceVertical(12.dp)

                        val user = detail!!.user
                        Text(
                            text = stringResource(R.string.all_full_name),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = user?.fullName.safe(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        SpaceVertical(12.dp)

                        Text(
                            text = stringResource(R.string.all_phone_number),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = user?.phoneDisplay.safe(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        SpaceVertical(12.dp)

                        Text(
                            text = stringResource(R.string.all_email),
                            fontSize = 12.sp,
                            color = Colors.Blue95
                        )

                        Text(
                            text = user?.email.safe(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        SpaceVertical(12.dp)

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Colors.Gray238
                        )

                        SpaceVertical(12.dp)

                        if (!detail!!.hasCancel) {
                            // Lawyer Info
                            Text(
                                stringResource(R.string.all_lawyer_infomation),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )

                            SpaceVertical(12.dp)

                            if (detail!!.lawyer == null)
                                Text(
                                    stringResource(R.string.all_finding_lawyer),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    color = Colors.Blue185,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            else LawyerInfo(it = detail!!.lawyer!!,
                                hasShowButton = detail!!.hasComplete,
                                hasShowReview = !detail!!.hasReview,
                                onDetail = {
                                    appNavigator.navigateDetailLawyer(
                                        dataJson = detail!!.owner.toJson()
                                    )
                                },
                                onReview = {appNavigator.navigateCreateReview(detail!!.owner)})
                        }

                        // Cancel Button
                        if (detail!!.hasShowButtonCancel) {
                            Spacer(modifier = Modifier.height(16.dp))
                            AppButton(
                                nameRes = R.string.all_cancel_request,
                                backgroundColor = Colors.Red247,
                                onClick = { hasShowCancel = true }
                            )
                        }

                        if (detail!!.hasCancel) {
                            Spacer(modifier = Modifier.height(16.dp))
                            AppButton(
                                nameRes = R.string.all_request_again,
                                onClick = { hasShowRequest = true }
                            )
                        }

                        if (hasShowRequest) {
                            AppConfirmDialog(
                                message = stringResource(R.string.msg_request_again),
                                textConfirm = stringResource(R.string.all_send_request),
                                onConfirm = {
                                    hasShowRequest = false
                                    viewModel.submitRequestAgain()
                                }, onDismiss = {
                                    hasShowRequest = false
                                }
                            )
                        }

                        if (hasShowCancel) {
                            CancellationReasonDialog(
                                onConfirm = {
                                    hasShowCancel = false
                                    viewModel.submitCancel(it)
                                }, onDismiss = {
                                    hasShowCancel = false
                                }
                            )
                        }
                    }
                } else NoDataView(htmlRes = R.string.no_data)
            }

        }
    }
}

class DetailCasesVM(
    private val appEvent: AppEvent,
    private val fetchBookingDetailRepo: FetchBookingDetailRepo,
    private val bookingRequestAgainRepo: BookingRequestAgainRepo,
    private val bookingCancelRepo: BookingCancelRepo,
    private val appNotifications: AppNotifications
) : AppViewModel() {
    val details = fetchBookingDetailRepo.result
    var requestID: Int = 0
    val onBack = MutableStateFlow(false)

    fun fetchDetails() = launch(loading, error) {
        fetchBookingDetailRepo(requestID)
        appNotifications.cancelNotification(requestID)
    }

    fun submitRequestAgain() = launch(loading, error) {
        bookingRequestAgainRepo(requestID)
        onBack.emit(true)
        appEvent.onRefreshMyCases.emit(true)
        appEvent.onRefreshNotification.emit(true)
    }

    fun submitCancel(reason: String) = launch(loading, error) {
        bookingCancelRepo(requestID, reason)
        fetchDetails()
        appEvent.onRefreshMyCases.emit(true)
        appEvent.onRefreshNotification.emit(true)
    }
}

class FetchBookingDetailRepo(
    private val bookingApi: BookingApi,
    private val bookingFactory: BookingFactory
) {
    val result = MutableStateFlow<IBookingDetail?>(null)
    suspend operator fun invoke(id: Int) {
        result.emit(bookingFactory.createDetails(bookingApi.details(id).awaitNullable()))
    }

}
