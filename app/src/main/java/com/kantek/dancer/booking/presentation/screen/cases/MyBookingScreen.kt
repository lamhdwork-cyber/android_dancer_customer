package com.kantek.dancer.booking.presentation.screen.cases

import android.support.core.event.LoadingEvent
import android.support.core.event.LoadingFlow
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.domain.extension.toJson
import com.kantek.dancer.booking.domain.factory.BookingFactory
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.booking.IBooking
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.CancellationReasonDialog
import com.kantek.dancer.booking.presentation.widget.CaseItemView
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.NoLoginView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyBookingScreen(viewModel: MyCasesVM = koinViewModel()) = ScopeProvider(Scopes.MyCase) {
    val context = LocalContext.current
    val appEvent = remember { get<AppEvent>() }
    val isRefreshingByEvent by appEvent.onRefreshMyCases.collectAsState()

    val myCase by viewModel.items.collectAsState()
    val user by viewModel.userLive.collectAsState(null)
    val isEmpty by viewModel.isEmpty.collectAsState()
    val appNavigator = use<AppNavigator>(Scopes.App)
    val isLoading by viewModel.customLoading.isLoading().collectAsState()
    val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()
    var hasShowComingSoon by remember { mutableStateOf(false) }
    var hasShowRequest by remember { mutableStateOf(false) }
    var hasShowCancel by remember { mutableStateOf(false) }
    val languageChanged by remember { mutableStateOf(viewModel.getCurrentLanguage()) }
    val userChanged by remember { mutableStateOf(viewModel.getCurrentUser()) }

    fun openAuth() {
        appNavigator.navigateSignIn()
    }

    LaunchedEffect(isRefreshingByEvent) {
        if (isRefreshingByEvent) {
            viewModel.onRefresh()
            appEvent.onRefreshMyCases.emit(false)
        }
    }

    LaunchedEffect(languageChanged) { viewModel.onChangeLanguage() }

    LaunchedEffect(userChanged) { viewModel.onChangeUser() }

    Column(modifier = Modifier.background(Colors.Gray249)) {
        ActionBarMainView(R.string.top_bar_my_booking)
        if (user == null) {
            NoLoginView(titleRes = R.string.my_cases_not_login) { openAuth() }
        }
        Box(modifier = Modifier.padding(top = 2.dp)) {
            AppLazyColumn(
                items = myCase,
                keyItem = { it.id },
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.onRefresh() },
                onLoadMore = { viewModel.onFetch() }
            ) { item, _, _ ->
                CaseItemView(
                    item,
                    onItemClick = { appNavigator.navigateDetailCase(item.id) },
                    onRequestClick = {
                        hasShowRequest = true
                        viewModel.requestID = item.id
                    },
                    onLawyerClick = {
                        appNavigator.navigateDetailLawyer(dataJson = item.owner.toJson())
                    },
                    onCancelClick = {
                        hasShowCancel = true
                        viewModel.requestID = item.id
                    },
                    onChatClick = {
//                        hasShowComingSoon = true
                        appNavigator.navigateConversation(item.id)
                    })
            }
            if (isEmpty)
                NoDataView(htmlRes = R.string.no_data_my_case)
        }
        if (hasShowComingSoon) {
            AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                hasShowComingSoon = false
            }
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
}

class MyCasesVM(
    private val fetchMyCaseByPageRepo: FetchMyCaseByPageRepo,
    private val bookingRequestAgainRepo: BookingRequestAgainRepo,
    private val bookingCancelRepo: BookingCancelRepo,
) : AppViewModel() {

    var requestID: Int = 0
    private val _items = MutableStateFlow<List<IBooking>>(emptyList())
    val items: StateFlow<List<IBooking>> = _items

    private val _isEmpty = MutableStateFlow(true)
    val isEmpty: StateFlow<Boolean> = _isEmpty

    val customLoading: LoadingEvent = LoadingFlow()
    val isRefreshLoading: LoadingEvent = LoadingFlow()

    private var page = 1
    private var hasMoreData = true

    fun onChangeLanguage() {
        if (currentLanguageBackup != getCurrentLanguage()) {
            currentLanguageBackup = getCurrentLanguage()
            onRefresh()
        }
    }

    fun onChangeUser() {
        if (currentUserBackup != getCurrentUser()) {
            currentUserBackup = getCurrentUser()
            onRefresh()
            Log.e("MyCasesScreen", "onChangeUser")
        }
    }

    fun onRefresh() {
        page = 1
        hasMoreData = true
        _items.value = emptyList()
        onFetch()
    }

    fun onFetch() {
        if (isRefreshLoading.isLoading().value
            || customLoading.isLoading().value
            || !hasMoreData
            || userLive.value == null
        ) return
        launch(if (page == 1) isRefreshLoading else customLoading, error) {
            val rs = fetchMyCaseByPageRepo(page)
            _isEmpty.value = (page == 1 && rs.isEmpty())
            if (rs.isEmpty()) hasMoreData = false
            else {
                if (rs.size < AppConfig.PER_PAGE) hasMoreData = false
                val current = _items.value
                val newItems = rs.filterNot { newItem ->
                    current.any { it.id == newItem.id }
                }
                _items.value = current + newItems
                page++
            }
        }
    }

    fun submitRequestAgain() = launch(loading, error) {
        bookingRequestAgainRepo(requestID)
        onRefresh()
    }

    fun submitCancel(reason: String) = launch(loading, error) {
        bookingCancelRepo(requestID, reason)
        onRefresh()
    }
}


class BookingCancelRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestID: Int, reason: String) {
        bookingApi.cancel(requestID, reason).await()
    }

}

class BookingRequestAgainRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestAgainID: Int) {
        bookingApi.recreate(requestAgainID).await()
    }

}

class FetchMyCaseByPageRepo(
    private val bookingApi: BookingApi,
    private val bookingFactory: BookingFactory
) {
    suspend operator fun invoke(page: Int): List<IBooking> {
        return bookingFactory.createList(bookingApi.fetchByPage(page).awaitNullable()?.data)
    }

}
