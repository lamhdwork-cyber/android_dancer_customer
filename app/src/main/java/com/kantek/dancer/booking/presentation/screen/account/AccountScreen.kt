package com.kantek.dancer.booking.presentation.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.ui.user.IUser
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.viewmodel.AccountVM
import com.kantek.dancer.booking.presentation.widget.ActionBarMainView
import com.kantek.dancer.booking.presentation.widget.AppConfirmDialog
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.LogoutDialog
import com.kantek.dancer.booking.presentation.widget.NoLoginView
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(viewModel: AccountVM = koinViewModel()) = ScopeProvider(Scopes.Account) {
    val context = LocalContext.current
    val user by viewModel.userLive.collectAsState()
    val onSignOut by viewModel.signOutSuccess.collectAsState()
    val appNavigator = use<AppNavigator>()
    val isDialogVisible = remember { mutableStateOf(false) }
    val isDeleteDialog = remember { mutableStateOf(false) }
    val hasShowComingSoon = remember { mutableStateOf(false) }

    fun openAuth() {
        appNavigator.navigateSignIn()
    }

    LaunchedEffect(onSignOut) {
        if (onSignOut) {
            isDialogVisible.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        ActionBarMainView(R.string.nav_account)
        if (user == null) {
            NoLoginView(titleRes = R.string.account_not_login) { openAuth() }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileSection(user = user!!)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .background(Colors.Pink0DF425F4)
                        .border(1.dp, Colors.Pink26F425F4, RoundedCornerShape(30.dp))
                        .padding(vertical = 8.dp)
                ) {
                    AccountMenuItem(
                        iconRes = R.drawable.ic_profile,
                        title = stringResource(R.string.account_edit_profile),
                        subtitle = stringResource(R.string.account_edit_profile_desc),
                        onClick = { appNavigator.navigateMyProfile() }
                    )
                    DividerLine()
                    AccountMenuItem(
                        iconRes = R.drawable.ic_nav_notification,
                        title = stringResource(R.string.account_notification_settings),
                        subtitle = stringResource(R.string.account_notification_settings_desc),
                        onClick = { hasShowComingSoon.value = true }
                    )
                    DividerLine()
                    AccountMenuItem(
                        iconRes = R.drawable.ic_language,
                        title = stringResource(R.string.all_language),
                        subtitle = stringResource(id = user!!.languageRes),
                        onClick = { appNavigator.navigateLanguage(true) }
                    )
                    DividerLine()
                    AccountMenuItem(
                        iconRes = R.drawable.ic_logout,
                        title = stringResource(R.string.all_logout),
                        subtitle = stringResource(R.string.account_logout_desc),
                        onClick = { isDialogVisible.value = true }
                    )
                }

                Text(
                    text = stringResource(R.string.account_danger_zone),
                    color = Colors.Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 6.dp)
                )
                AccountDangerItem(
                    iconRes = R.drawable.ic_delete,
                    title = stringResource(R.string.account_delete),
                    subtitle = stringResource(R.string.account_delete_desc),
                    onClick = { isDeleteDialog.value = true }
                )
            }
        }

        if (isDialogVisible.value) LogoutDialog(onDismiss = {
            isDialogVisible.value = false
        }, onLogout = {
            isDialogVisible.value = false
            viewModel.logout()
        })
        if (isDeleteDialog.value) {
            AppConfirmDialog(title = stringResource(R.string.all_delete_account),
                message = stringResource(R.string.all_msg_delete_app),
                textConfirm = stringResource(R.string.all_delete),
                onConfirm = {
                    isDeleteDialog.value = false
                    viewModel.delete()
                },
                onDismiss = {
                    isDeleteDialog.value = false
                })
        }
        if (hasShowComingSoon.value) {
            AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                hasShowComingSoon.value = false
            }
        }
    }
}

@Composable
private fun ProfileSection(user: IUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(176.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(154.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = CircleShape,
                        ambientColor = Colors.Primary,
                        spotColor = Colors.Primary
                    )
                    .clip(CircleShape)
                    .background(Colors.Pink33F425F4)
            )
            Box(
                modifier = Modifier
                    .size(142.dp)
                    .clip(CircleShape)
                    .background(Colors.Dark120812)
                    .padding(3.dp)
            ) {
                AsyncImage(
                    model = user.avatarURL,
                    contentDescription = user.fullName,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-16).dp, y = (-16).dp)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Colors.Primary)
                    .border(3.dp, Colors.Dark120812, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PhotoCamera,
                    contentDescription = null,
                    tint = Colors.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Text(
            text = user.fullName,
            color = Colors.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = user.email,
            color = Colors.Primary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun AccountMenuItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Colors.Pink33F425F4),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Colors.Primary,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Colors.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = subtitle, color = Colors.Pink99F425F4, fontSize = 12.sp)
        }
        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = Colors.Primary.copy(alpha = 0.45f),
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun AccountDangerItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Colors.Red1AEF4444)
            .border(1.dp, Colors.Red33EF4444, RoundedCornerShape(28.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Colors.Red1AEF4444),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Colors.RedEF4444,
                modifier = Modifier.size(20.dp)
            )
        }
        Column {
            Text(text = title, color = Colors.RedEF4444, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = subtitle, color = Colors.Red99EF4444, fontSize = 12.sp)
        }
    }
}

@Composable
private fun DividerLine() {
    Spacer(
        modifier = Modifier
            .padding(start = 66.dp, end = 16.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(Colors.Pink26F425F4)
    )
}