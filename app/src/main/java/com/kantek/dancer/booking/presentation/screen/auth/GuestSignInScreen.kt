package com.kantek.dancer.booking.presentation.screen.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Nightlife
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.presentation.MainAct
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.viewmodel.SignInVM
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.AppNotificationDialog
import com.kantek.dancer.booking.presentation.widget.SpaceHorizontal
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun GuestSignInScreen(
    hasInApp: Boolean,
    viewModel: SignInVM = koinViewModel()
) = ScopeProvider {

    val context = LocalContext.current
    val formState by viewModel.formState.collectAsState()
    val openMain by viewModel.loginSuccess.collectAsState()
    val appNavigator = use<AppNavigator>()
    val hasShowComingSoon = remember { mutableStateOf(false) }

    BackHandler {
        appNavigator.back()
    }

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
            .background(Colors.Dark120812)
    ) {
        GuestSignInMeshBackground(Modifier.matchParentSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            ActionBarBackAndTitleView(
                R.string.auth_guest_screen_title,
                Color.Transparent
            ) { appNavigator.back() }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpaceVertical(24.dp)

                GuestSignInBrandHeader()

                SpaceVertical(28.dp)

                GuestSignInFormSection(
                    account = formState.account,
                    password = formState.password,
                    onAccountChange = { viewModel.updateAccount(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onSignInClick = { viewModel.signIn() },
                    onForgotPasswordClick = { appNavigator.navigateForgotPassword() }
                )
            }

            GuestSignInSignUpFooter(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                onSignUpClick = { appNavigator.navigateSignUp() }
            )
        }

        if (hasShowComingSoon.value) {
            AppNotificationDialog(stringResource(R.string.all_coming_soon)) {
                hasShowComingSoon.value = false
            }
        }
    }
}

@Composable
private fun GuestSignInMeshBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.drawBehind {
            drawRect(Colors.Dark120812)
            val r = size.maxDimension * 0.55f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Colors.Pink26F425F4, Color.Transparent),
                    center = Offset.Zero,
                    radius = r * 1.2f
                ),
                radius = r,
                center = Offset.Zero
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Colors.Pink1AF425F4, Color.Transparent),
                    center = Offset(size.width, size.height),
                    radius = r
                ),
                radius = r * 0.9f,
                center = Offset(size.width, size.height)
            )
        }
    )
}

@Composable
private fun GuestSignInBrandHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Colors.Pink33F425F4)
                .border(1.dp, Colors.Pink4DF425F4, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Nightlife,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Colors.Primary
            )
        }
        SpaceVertical(24.dp)
        Text(
            text = stringResource(R.string.auth_guest_brand_title),
            color = Colors.GrayF1F5F9,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = (-0.5).sp
        )
        SpaceVertical(8.dp)
        Text(
            text = stringResource(R.string.auth_guest_brand_subtitle),
            color = Colors.Blue148,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GuestSignInFormSection(
    account: String,
    password: String,
    onAccountChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
                .size(width = 320.dp, height = 220.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Colors.Pink0DF425F4, Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            AppInputText(
                value = account,
                placeHolderRes = R.string.auth_guest_label_email,
                hintRes = R.string.auth_guest_hint_email,
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = onAccountChange
            )
            SpaceVertical(20.dp)
            AppInputText(
                value = password,
                placeHolderRes = R.string.all_password,
                hintRes = R.string.auth_guest_hint_password,
                leadingIcon = Icons.Outlined.Lock,
                isPassword = true,
                onValueChange = onPasswordChange
            )
            SpaceVertical(40.dp)
            AppButton(
                nameRes = R.string.all_sign_in,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = Colors.Pink33F425F4,
                        ambientColor = Colors.Pink33F425F4
                    ),
                fontSize = 18
            ) {
                onSignInClick()
            }
            SpaceVertical(24.dp)
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.auth_forgot_pw),
                    color = Colors.Gray6B7280,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )
            }
        }
    }
}

@Composable
private fun GuestSignInSignUpFooter(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.auth_guest_footer_no_account),
            fontSize = 14.sp,
            color = Colors.Blue148
        )
        SpaceHorizontal(4.dp)
        Text(
            text = stringResource(R.string.all_sign_up),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.Primary,
            modifier = Modifier.clickable { onSignUpClick() }
        )
    }
}
