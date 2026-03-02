package com.kantek.dancer.booking.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.data.event.AppEvent
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.viewmodel.NotificationVM
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.HtmlStyledText
import com.kantek.dancer.booking.presentation.widget.NoDataView
import com.kantek.dancer.booking.presentation.widget.NoLoginView
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(viewModel: NotificationVM = koinViewModel()) =
    ScopeProvider(Scopes.Notification) {
        val appEvent = remember { get<AppEvent>() }
        val isRefreshingByEvent by appEvent.onRefreshNotification.collectAsState()

        val appNavigator = use<AppNavigator>(Scopes.App)
        val notifications by viewModel.items.collectAsState()
        val user by viewModel.userLive.collectAsState(null)
        val isEmpty by viewModel.isEmpty.collectAsState()
        val isLoading by viewModel.customLoading.isLoading().collectAsState()
        val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()
        val hasShowComingSoon = remember { mutableStateOf(false) }
        val languageChanged by remember { mutableStateOf(viewModel.getCurrentLanguage()) }
        val userChanged by remember { mutableStateOf(viewModel.getCurrentUser()) }

        fun openAuth() {
            appNavigator.navigateSignIn()
        }

        LaunchedEffect(languageChanged) { viewModel.onChangeLanguage() }

        LaunchedEffect(isRefreshingByEvent) {
            if (isRefreshingByEvent) {
                viewModel.onRefresh()
                appEvent.onRefreshNotification.emit(false)
            }
        }

        LaunchedEffect(userChanged) { viewModel.onChangeUser() }

        Column(modifier = Modifier.background(Colors.Gray249)) {
            ActionBarMainView(R.string.nav_notification)
            if (user == null) {
                NoLoginView(titleRes = R.string.notification_not_login) { openAuth() }
            }
            Box(modifier = Modifier.padding(top = 2.dp)) {
                AppLazyColumn(
                    items = notifications,
                    keyItem = { it.id },
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    isLoading = isLoading,
                    isRefreshing = isRefreshing,
                    onRefresh = { viewModel.onRefresh() },
                    onLoadMore = { viewModel.onFetch() }
                ) { item, _, _ ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = { appNavigator.navigateDetailCase(item.bookingID) }
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = item.title,
                                Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                            HtmlStyledText(
                                html = item.contents,
                                modifier = Modifier.padding(16.dp, 5.dp, 16.dp, 0.dp)
                            )
                            Text(
                                text = item.datetime,
                                Modifier.padding(16.dp, 5.dp, 16.dp, 16.dp),
                                fontWeight = FontWeight.Light,
                                fontSize = 13.sp
                            )
                        }

                    }
                }
                if (isEmpty)
                    NoDataView(htmlRes = R.string.no_data_notifications)
            }
            if (hasShowComingSoon.value) {
                AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                    hasShowComingSoon.value = false
                }
            }
        }
    }