package com.kantek.dancer.booking.presentation.screen.booking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppConfig
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.data.remote.api.BookingApi
import com.kantek.dancer.booking.data.factory.BookingFactory
import com.kantek.dancer.booking.presentation.model.support.Scopes
import com.kantek.dancer.booking.domain.model.booking.IBooking
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.BookingItemView
import com.kantek.dancer.booking.presentation.widget.CancellationReasonDialog
import com.kantek.dancer.booking.presentation.widget.NoLoginView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

enum class MyBookingTab(val apiStatus: String) {
    PENDING(AppConfig.Booking.Status.PENDING),
    ACCEPTED(AppConfig.Booking.Status.CONFIRMED),
    COMPLETED(AppConfig.Booking.Status.COMPLETED)
}

private sealed interface ManagerBookingConfirm {
    data class Accept(val tab: MyBookingTab) : ManagerBookingConfirm
    data class Complete(val tab: MyBookingTab) : ManagerBookingConfirm
}

data class TabBookingState(
    val items: List<IBooking> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val page: Int = 1,
    val hasMoreData: Boolean = true,
    val initialized: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyBookingScreen(viewModel: MyBookingVM = koinViewModel()) = ScopeProvider(Scopes.MyBooking) {
    val context = LocalContext.current
    val appEvent = remember { get<AppEvent>() }
    val isRefreshingByEvent by appEvent.onRefreshMyBooking.collectAsState()

    val user by viewModel.userLive.collectAsState(null)
    val appNavigator = use<AppNavigator>()
    var hasShowComingSoon by remember { mutableStateOf(false) }
    var hasShowRequest by remember { mutableStateOf(false) }
    var hasShowCancel by remember { mutableStateOf(false) }
    var managerBookingConfirm by remember { mutableStateOf<ManagerBookingConfirm?>(null) }
    val languageChanged by remember { mutableStateOf(viewModel.getCurrentLanguage()) }
    val userChanged by remember { mutableStateOf(viewModel.getCurrentUser()) }
    val pagerState = rememberPagerState(pageCount = { MyBookingTab.entries.size })
    val coroutineScope = rememberCoroutineScope()

    fun openAuth() {
        appNavigator.navigateSignIn()
    }

    LaunchedEffect(isRefreshingByEvent) {
        if (isRefreshingByEvent) {
            viewModel.refreshAll()
            MyBookingTab.entries.forEach { tab ->
                viewModel.ensureLoaded(tab)
            }
            appEvent.onRefreshMyBooking.emit(false)
        }
    }

    LaunchedEffect(languageChanged) {
        viewModel.onChangeLanguage()
        viewModel.ensureLoaded(MyBookingTab.entries[pagerState.currentPage])
    }

    LaunchedEffect(userChanged) {
        viewModel.onChangeUser()
        viewModel.ensureLoaded(MyBookingTab.entries[pagerState.currentPage])
    }

    LaunchedEffect(pagerState.currentPage, user?.id) {
        if (user != null) {
            viewModel.ensureLoaded(MyBookingTab.entries[pagerState.currentPage])
        }
    }

    val topBarTitleRes by viewModel.topBarTitleRes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        ActionBarMainView(topBarTitleRes)
        if (user == null) {
            NoLoginView(titleRes = R.string.my_cases_not_login) { openAuth() }
        } else {
            MyBookingTabs(
                selectedTab = MyBookingTab.entries[pagerState.currentPage],
                onTabSelected = { tab ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(tab.ordinal)
                    }
                }
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Colors.Dark190C19)
            ) { page ->
                val tab = MyBookingTab.entries[page]
                val tabState by viewModel.stateOf(tab).collectAsState()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Colors.Dark190C19)
                ) {
                    val noDataRes = when (tab) {
                        MyBookingTab.PENDING -> R.string.no_data_my_booking
                        MyBookingTab.ACCEPTED -> R.string.no_data_my_booking_accepted
                        MyBookingTab.COMPLETED -> R.string.no_data_my_booking_completed
                    }
                    AppLazyColumn(
                        items = tabState.items,
                        keyItem = { it.id },
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        isLoading = tabState.isLoading,
                        isRefreshing = tabState.isRefreshing,
                        onRefresh = { viewModel.onRefresh(tab) },
                        onLoadMore = { viewModel.onFetch(tab) },
                        emptyHtmlRes = noDataRes,
                        isEmpty = tabState.items.isEmpty(),
                        modifier = Modifier.fillMaxSize()
                    ) { item, _, _ ->
                        BookingItemView(
                            item,
                            onItemClick = { appNavigator.navigateDetailCase(item.id) },
                            onRequestClick = {
                                hasShowRequest = true
                                viewModel.requestID = item.id
                            },
                            onCancelClick = {
                                hasShowCancel = true
                                viewModel.requestID = item.id
                            },
                            onAcceptClick = {
                                viewModel.requestID = item.id
                                managerBookingConfirm = ManagerBookingConfirm.Accept(tab)
                            },
                            onCompleteClick = {
                                viewModel.requestID = item.id
                                managerBookingConfirm = ManagerBookingConfirm.Complete(tab)
                            }
                        )
                    }
                }
            }
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
                    viewModel.submitRequestAgain(MyBookingTab.entries[pagerState.currentPage])
                }, onDismiss = {
                    hasShowRequest = false
                }
            )
        }
        if (hasShowCancel) {
            CancellationReasonDialog(
                onConfirm = {
                    hasShowCancel = false
                    viewModel.submitCancel(
                        reason = it,
                        tab = MyBookingTab.entries[pagerState.currentPage]
                    )
                }, onDismiss = {
                    hasShowCancel = false
                }
            )
        }
        when (val confirm = managerBookingConfirm) {
            is ManagerBookingConfirm.Accept -> AppConfirmDialog(
                title = stringResource(R.string.booking_confirm_accept_title),
                message = stringResource(R.string.booking_confirm_accept_message),
                textConfirm = stringResource(R.string.all_confirm),
                onConfirm = {
                    managerBookingConfirm = null
                    viewModel.submitAccept(confirm.tab)
                },
                onDismiss = { managerBookingConfirm = null }
            )

            is ManagerBookingConfirm.Complete -> AppConfirmDialog(
                title = stringResource(R.string.booking_confirm_complete_title),
                message = stringResource(R.string.booking_confirm_complete_message),
                textConfirm = stringResource(R.string.all_confirm),
                onConfirm = {
                    managerBookingConfirm = null
                    viewModel.submitComplete(confirm.tab)
                },
                onDismiss = { managerBookingConfirm = null }
            )

            null -> Unit
        }
    }
}

@Composable
private fun MyBookingTabs(
    selectedTab: MyBookingTab,
    onTabSelected: (MyBookingTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Dark120812)
    ) {
        val tabs = listOf(
            MyBookingTab.PENDING to stringResource(R.string.booking_tab_pending),
            MyBookingTab.ACCEPTED to stringResource(R.string.booking_tab_accepted),
            MyBookingTab.COMPLETED to stringResource(R.string.booking_tab_completed)
        )

        tabs.forEach { (tab, title) ->
            val isSelected = tab == selectedTab
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) }
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Colors.Primary else Colors.Gray6B7280,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                if (isSelected) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = Colors.Primary
                    )
                } else {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

class MyBookingVM(
    private val fetchMyBookingByPageRepo: FetchMyBookingByPageRepo,
    private val bookingRequestAgainRepo: BookingRequestAgainRepo,
    private val bookingCancelRepo: BookingCancelRepo,
    private val bookingAcceptRepo: BookingAcceptRepo,
    private val bookingCompleteRepo: BookingCompleteRepo,
) : AppViewModel() {

    val topBarTitleRes: StateFlow<Int> = userLive.map { user ->
        if (user != null && AppConfig.UserRole.isClubManager(user.role)) {
            R.string.top_bar_booking_queue
        } else {
            R.string.top_bar_my_booking
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        R.string.top_bar_my_booking
    )

    var requestID: String = ""
    private val _pendingState = MutableStateFlow(TabBookingState())
    private val _acceptedState = MutableStateFlow(TabBookingState())
    private val _completedState = MutableStateFlow(TabBookingState())

    private fun mutableStateOf(tab: MyBookingTab): MutableStateFlow<TabBookingState> = when (tab) {
        MyBookingTab.PENDING -> _pendingState
        MyBookingTab.ACCEPTED -> _acceptedState
        MyBookingTab.COMPLETED -> _completedState
    }

    fun stateOf(tab: MyBookingTab): StateFlow<TabBookingState> = when (tab) {
        MyBookingTab.PENDING -> _pendingState
        MyBookingTab.ACCEPTED -> _acceptedState
        MyBookingTab.COMPLETED -> _completedState
    }

    fun onChangeLanguage() {
        if (currentLanguageBackup != getCurrentLanguage()) {
            currentLanguageBackup = getCurrentLanguage()
            refreshAll()
        }
    }

    fun onChangeUser() {
        if (currentUserBackup != getCurrentUser()) {
            currentUserBackup = getCurrentUser()
            refreshAll()
        }
    }

    fun refreshAll() {
        _pendingState.value = TabBookingState()
        _acceptedState.value = TabBookingState()
        _completedState.value = TabBookingState()
    }

    fun ensureLoaded(tab: MyBookingTab) {
        if (!stateOf(tab).value.initialized) onFetch(tab)
    }

    fun onRefresh(tab: MyBookingTab) {
        mutableStateOf(tab).value = TabBookingState()
        onFetch(tab)
    }

    fun onFetch(tab: MyBookingTab) {
        if (userLive.value == null) return
        val flow = mutableStateOf(tab)
        val state = flow.value
        if (state.isLoading
            || state.isRefreshing
            || !state.hasMoreData
        ) return

        val isFirstPage = state.page == 1
        launch(null, error) {
            val busy = if (isFirstPage) {
                state.copy(isRefreshing = true)
            } else {
                state.copy(isLoading = true)
            }
            flow.value = busy
            try {
                val rs = fetchMyBookingByPageRepo(page = busy.page, status = tab.apiStatus)
                val hasMore = rs.size >= AppConfig.PER_PAGE
                val merged = (busy.items + rs).distinctBy { it.id }
                flow.value = busy.copy(
                    items = merged,
                    page = if (rs.isEmpty()) busy.page else busy.page + 1,
                    hasMoreData = hasMore,
                    isLoading = false,
                    isRefreshing = false,
                    initialized = true
                )
            } finally {
                val cur = flow.value
                if (cur.isLoading || cur.isRefreshing) {
                    flow.value = cur.copy(
                        isLoading = false,
                        isRefreshing = false,
                        initialized = true
                    )
                }
            }
        }
    }

    fun submitRequestAgain(tab: MyBookingTab) = launch(loading, error) {
        bookingRequestAgainRepo(requestID)
        onRefresh(tab)
    }

    fun submitCancel(reason: String, tab: MyBookingTab) = launch(loading, error) {
        bookingCancelRepo(requestID, reason)
        onRefresh(tab)
    }

    fun submitAccept(tab: MyBookingTab) = launch(loading, error) {
        bookingAcceptRepo(requestID)
        onRefresh(tab)
    }

    fun submitComplete(tab: MyBookingTab) = launch(loading, error) {
        bookingCompleteRepo(requestID)
        onRefresh(tab)
        if (tab != MyBookingTab.COMPLETED) {
            onRefresh(MyBookingTab.COMPLETED)
        }
    }
}


class BookingCancelRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestID: String, reason: String) {
        bookingApi.cancel(requestID, reason).awaitNullable()
    }

}

class BookingAcceptRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestID: String) {
        bookingApi.accept(requestID).awaitNullable()
    }
}

class BookingCompleteRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestID: String) {
        bookingApi.complete(requestID).awaitNullable()
    }
}

class BookingRequestAgainRepo(private val bookingApi: BookingApi) {

    suspend operator fun invoke(requestAgainID: String) {
        bookingApi.recreate(requestAgainID).await()
    }

}

class FetchMyBookingByPageRepo(
    private val bookingApi: BookingApi,
    private val bookingFactory: BookingFactory
) {
    suspend operator fun invoke(page: Int, status: String): List<IBooking> {
        return bookingFactory.createList(
            bookingApi.fetchByPage(page = page, status = status).awaitNullable()?.items
        )
    }

}
