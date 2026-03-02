package com.kantek.dancer.booking.presentation.screen.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
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
import com.kantek.dancer.booking.presentation.widget.ProfileHeader
import com.kantek.dancer.booking.presentation.widget.SettingItem
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(viewModel: AccountVM = koinViewModel()) = ScopeProvider(Scopes.Account) {
    val context = LocalContext.current
    val user by viewModel.userLive.collectAsState()
    val onSignOut by viewModel.signOutSuccess.collectAsState()
    val appNavigator = use<AppNavigator>(Scopes.App)
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
            .background(Colors.Gray249)
    ) {
        ActionBarMainView(R.string.nav_account)
        if (user == null) {
            NoLoginView(titleRes = R.string.account_not_login) { openAuth() }
        } else Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            ProfileHeader(user)
            SpaceVertical(5.dp)
            SettingItem(iconRes = R.drawable.ic_profile, titleRes = R.string.account_profile) {
                appNavigator.navigateMyProfile()
            }
            SettingItem(iconRes = R.drawable.ic_password, titleRes = R.string.account_change_pw) {
                appNavigator.navigateChangePassword()
            }
            SettingItem(
                iconRes = R.drawable.ic_language,
                titleRes = R.string.all_language,
                subtitleRes = user?.languageRes
            ) { appNavigator.navigateLanguage(true) }
            SettingItem(
                iconRes = R.drawable.ic_terms_conditions, titleRes = R.string.account_terms
            ) {
                appNavigator.navigateTerms()
//                hasShowComingSoon.value=true
            }
            SettingItem(iconRes = R.drawable.ic_about, titleRes = R.string.account_about_us) {
                appNavigator.navigateAboutUs()
//                hasShowComingSoon.value=true
            }
            SettingItem(iconRes = R.drawable.ic_faq_threads, titleRes = R.string.account_faq_threads) {
                appNavigator.navigateFAQThreads()
            }
            SettingItem(iconRes = R.drawable.ic_contact, titleRes = R.string.all_contact_us) {
                appNavigator.navigateContactUs()
            }
            SettingItem(
                iconRes = R.drawable.ic_delete, titleRes = R.string.account_delete, isDanger = true
            ) { isDeleteDialog.value = true }
            SettingItem(iconRes = R.drawable.ic_logout, titleRes = R.string.all_logout) {
                isDialogVisible.value = true
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
}