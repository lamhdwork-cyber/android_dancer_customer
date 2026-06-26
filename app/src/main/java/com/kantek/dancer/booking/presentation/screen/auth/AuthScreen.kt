package com.kantek.dancer.booking.presentation.screen.auth
import android.support.ui.extension.rememberDebouncedClick
import android.support.ui.extension.onClick

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppScopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.navigation.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import android.support.ui.widget.AppButton
import android.support.ui.widget.AppNextButton

@Composable
fun AuthScreen() = ScopeProvider(AppScopes.AppRole) {

    val appNavigator = use<AppNavigator>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(AppRoleStyle.BackgroundGradient))
    ) {
        Image(
            painter = painterResource(AppRoleStyle.BackgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(AppRoleStyle.BackgroundOverlayGradient))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppRoleStyle.ScreenHorizontalPadding, vertical = 72.dp)
        ) {
            Text(
                text = stringResource(R.string.role_title_welcome),
                color = Colors.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.role_subtitle_description),
                color = AppRoleStyle.SubTextColor,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 22.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(AppRoleStyle.CardSpacing)
            ) {
                AppRoleCard(
                    iconRes = R.drawable.ic_user,
                    accentColor = AppRoleStyle.PrimaryAccent,
                    badgeRes = R.string.role_guest_badge,
                    titleRes = R.string.role_guest_title,
                    descriptionRes = R.string.role_guest_description,
                    buttonRes = R.string.role_guest_action,
                    isGuest = true,
                    onClick = rememberDebouncedClick { appNavigator.navigateSignIn() }
                )
                AppRoleCard(
                    iconRes = R.drawable.ic_profile,
                    accentColor = AppRoleStyle.GoldAccent,
                    badgeRes = R.string.role_manager_badge,
                    titleRes = R.string.role_manager_title,
                    descriptionRes = R.string.role_manager_description,
                    buttonRes = R.string.role_manager_action,
                    isGuest = false,
                    onClick = rememberDebouncedClick { appNavigator.navigateManageStaffSignIn() }
                )
            }

            Text(
                text = stringResource(R.string.role_footer_terms),
                color = AppRoleStyle.FooterColor,
                fontSize = 11.sp,
                letterSpacing = 1.2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun AppRoleCard(
    iconRes: Int,
    accentColor: Color,
    badgeRes: Int,
    titleRes: Int,
    descriptionRes: Int,
    buttonRes: Int,
    isGuest: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = AppRoleStyle.CardMinHeight)
            .background(AppRoleStyle.CardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, AppRoleStyle.CardBorder, RoundedCornerShape(16.dp))
            .onClick { onClick() }
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(accentColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = stringResource(badgeRes),
            color = accentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(top = 14.dp)
        )
        Text(
            text = stringResource(titleRes),
            color = Colors.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = stringResource(descriptionRes),
            color = AppRoleStyle.SubTextColor,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 14.dp)
        )

        if (isGuest) {
            AppNextButton(
                nameRes = buttonRes,
                backgroundColor = accentColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppRoleStyle.ActionButtonHeight),
                onClick = rememberDebouncedClick(onClick = onClick)
            )
        } else {
            AppButton(
                nameRes = buttonRes,
                backgroundColor = Colors.White.copy(alpha = 0.1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppRoleStyle.ActionButtonHeight),
                onClick = rememberDebouncedClick(onClick = onClick)
            )
        }
    }
}

private object AppRoleStyle {
    val BackgroundImageRes = R.drawable.bg_app_role
    val BackgroundGradient = listOf(
        Colors.Dark120812,
        Colors.Dark190C19,
        Colors.Dark0D070D
    )
    val BackgroundOverlayGradient = listOf(
        Colors.OverlayCC120812,
        Colors.Overlay99120812,
        Colors.Dark120812
    )
    val PrimaryAccent = Colors.Primary
    val GoldAccent = Colors.GoldFFD700
    val CardBackground = Colors.White.copy(alpha = 0.06f)
    val CardBorder = Colors.White.copy(alpha = 0.12f)
    val SubTextColor = Colors.Gray9CA3AF
    val FooterColor = Colors.Gray6B7280
    val ScreenHorizontalPadding = 24.dp
    val CardSpacing = 14.dp
    val CardMinHeight = 250.dp
    val ActionButtonHeight = 56.dp
}
