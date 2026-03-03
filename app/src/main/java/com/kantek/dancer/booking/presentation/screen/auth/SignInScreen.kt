package com.kantek.dancer.booking.presentation.screen.auth

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.MainAct
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.helper.login.GoogleSignInButton
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.viewmodel.SignInVM
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    hasInApp: Boolean,
    viewModel: SignInVM = koinViewModel()
) = ScopeProvider {
    val context = LocalContext.current
    val formState by viewModel.formState.collectAsState()
    val openMain by viewModel.loginSuccess.collectAsState()
    val appNavigator = use<AppNavigator>(Scopes.App)
    val hasShowComingSoon = remember { mutableStateOf(false) }

    fun openMain() {
        val intent = Intent(context, MainAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

    LaunchedEffect(openMain) {
        if (openMain) {
            if (hasInApp) {
                (context as? Activity)?.recreate()
                appNavigator.back()
            } else openMain()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.img_logo_app_2),
//                contentDescription = "Logo",
//                modifier = Modifier.size(100.dp),
//                contentScale = ContentScale.Fit
//            )
            Column(Modifier.weight(1f)) { }
            SpaceVertical(30.dp)
            Text(
                text = "DANCER",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Colors.Primary,
                textAlign = TextAlign.Center
            )
            Column(Modifier.weight(1f)) { }
            SpaceVertical(30.dp)
            Column {
                AppInputText(
                    value = formState.account,
                    placeHolderRes = R.string.all_phone_or_email,
                    leadingIconRes = R.drawable.ic_user,
                    onValueChange = { viewModel.updateAccount(it) }
                )
                SpaceVertical(16.dp)
                AppInputText(
                    value = formState.password,
                    placeHolderRes = R.string.all_password,
                    leadingIconRes = R.drawable.ic_password,
                    isPassword = true,
                    maxLength = 6,
                    onValueChange = { viewModel.updatePassword(it) }
                )
                SpaceVertical(30.dp)
                AppButton(R.string.all_sign_in) {
                    viewModel.signIn()
                }
                SpaceVertical(30.dp)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.auth_forgot_pw),
                        color = Colors.Primary,
                        modifier = Modifier.clickable {
//                            hasShowComingSoon.value = true
                            appNavigator.navigateForgotPassword()
                        }
                    )
                }
            }
            Column(Modifier.weight(1f)) { }
            SpaceVertical(50.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Colors.Gray238)
                SpaceHorizontal(10.dp)
                Text(
                    text = stringResource(R.string.auth_with),
                    fontSize = 14.sp,
                    color = Colors.Gray146
                )
                SpaceHorizontal(10.dp)
                HorizontalDivider(modifier = Modifier.weight(1f), color = Colors.Gray238)
            }
            SpaceVertical(16.dp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
//                SocialLoginButton(R.drawable.ic_apple) { hasShowComingSoon.value = true }
                GoogleSignInButton { viewModel.loginGoogle(it) }
            }
            SpaceVertical(50.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.auth_first_time_here),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                SpaceHorizontal(30.dp)
                AppButton(
                    nameRes = R.string.all_sign_up,
                    backgroundColor = Colors.Blue241,
                    textColor = Colors.Primary,
                    modifier = Modifier.height(55.dp)
                ) {
                    appNavigator.navigateSignUp()
                }
            }
            if (hasShowComingSoon.value) {
                AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                    hasShowComingSoon.value = false
                }
            }
        }
    }
}