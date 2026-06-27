package com.hdl.dancer.booking.presentation.screen.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.data.event.AppEvent
import com.hdl.dancer.booking.presentation.model.support.Scopes
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.extensions.use
import com.hdl.dancer.booking.presentation.helper.AppNavigator
import com.hdl.dancer.booking.domain.model.notification.INotification
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.viewmodel.NotificationVM
import com.hdl.dancer.booking.presentation.widget.ActionBarMainView
import com.hdl.dancer.booking.presentation.widget.AppConfirmDialog
import com.hdl.dancer.booking.presentation.widget.AppLazyColumn
import com.hdl.dancer.booking.presentation.widget.AppNotificationDialog
import com.hdl.dancer.booking.presentation.widget.NoLoginView
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(viewModel: NotificationVM = koinViewModel()) =
    ScopeProvider(Scopes.Notification) {
        val appEvent = remember { get<AppEvent>() }
        val isRefreshingByEvent by appEvent.onRefreshNotification.collectAsState()

        val appNavigator = use<AppNavigator>()
        val notifications by viewModel.items.collectAsState()
        val user by viewModel.userLive.collectAsState(null)
        val isLoading by viewModel.customLoading.isLoading().collectAsState()
        val isRefreshing by viewModel.isRefreshLoading.isLoading().collectAsState()
        val hasShowComingSoon = remember { mutableStateOf(false) }
        var showReadAllConfirm by remember { mutableStateOf(false) }
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Dark120812)
        ) {
            val showReadAllIcon = user != null && notifications.isNotEmpty()
            ActionBarMainView(
                textRes = R.string.nav_notification,
                iconRight = if (showReadAllIcon) Icons.Outlined.DoneAll else null,
                onClickIconRight = if (showReadAllIcon) {
                    { showReadAllConfirm = true }
                } else {
                    null
                }
            )
            if (user == null) {
                NoLoginView(titleRes = R.string.notification_not_login) { openAuth() }
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    AppLazyColumn(
                        items = notifications,
                        keyItem = { it.id },
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        isLoading = isLoading,
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.onRefresh() },
                        onLoadMore = { viewModel.onFetch() },
                        emptyHtmlRes = R.string.no_data_notifications,
                        isEmpty = notifications.isEmpty(),
                        modifier = Modifier.fillMaxSize()
                    ) { item, _, _ ->
                        NotificationItemView(item = item) {
                            viewModel.onNotificationItemClick(item) { bookingId ->
                                appNavigator.navigateDetailCase(bookingId)
                            }
                        }
                    }
                }
            }
            if (hasShowComingSoon.value) {
                AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                    hasShowComingSoon.value = false
                }
            }
            if (showReadAllConfirm) {
                AppConfirmDialog(
                    title = stringResource(R.string.notification_read_all_confirm_title),
                    message = stringResource(R.string.notification_read_all_confirm_message),
                    textConfirm = stringResource(R.string.all_confirm),
                    onConfirm = {
                        showReadAllConfirm = false
                        viewModel.readAll()
                    },
                    onDismiss = { showReadAllConfirm = false }
                )
            }
        }
    }

@Composable
private fun NotificationItemView(
    item: INotification,
    onClick: () -> Unit
) {
    val isUnread = item.hasUnRead
    val leadingIconContainerColor = if (isUnread) Colors.Pink1AF425F4 else Colors.White1AFFFFFF
    val leadingIconTintColor = if (isUnread) Colors.Primary else Colors.Gray6B7280
    val titleColor = if (isUnread) Colors.White else Colors.Gray9CA3AF
    val contentColor = if (isUnread) Colors.Gray9CA3AF else Colors.Gray6B7280
    val chevronTintColor = if (isUnread) Colors.Pink33F425F4 else Colors.Gray6B7280

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Dark190C19),
        border = BorderStroke(1.dp, Colors.White1AFFFFFF)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.Dark190C19)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(leadingIconContainerColor, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = leadingIconTintColor,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        color = titleColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    if (item.hasUnRead) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .height(20.dp)
                                .background(Colors.Pink1AF425F4, CircleShape)
                                .padding(horizontal = 8.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.notification_item_badge_new),
                                color = Colors.Primary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Text(
                    text = item.contents,
                    color = contentColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = item.datetime,
                    color = Colors.Gray6B7280,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = chevronTintColor,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}