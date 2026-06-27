package com.hdl.dancer.booking.presentation.screen.auth

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.presentation.MainAct
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.extensions.use
import com.hdl.dancer.booking.presentation.helper.AppNavigator
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.viewmodel.ManageStaffSignInVM
import com.hdl.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.hdl.dancer.booking.presentation.widget.AppButton
import com.hdl.dancer.booking.presentation.widget.AppInputText
import com.hdl.dancer.booking.presentation.widget.SpaceVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun ManageStaffSignInScreen(
    hasInApp: Boolean,
    viewModel: ManageStaffSignInVM = koinViewModel()
) = ScopeProvider {

    val context = LocalContext.current
    val formState by viewModel.formState.collectAsState()
    val openMain by viewModel.loginSuccess.collectAsState()
    val appNavigator = use<AppNavigator>()

    fun navigateToMain() {
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
            } else {
                navigateToMain()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        ManageStaffSignInMeshBackground(Modifier.matchParentSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            ActionBarBackAndTitleView(
                R.string.auth_manage_staff_title,
                Color.Transparent
            ) { appNavigator.back() }
//            ManageStaffSignInTopBar(onBack = { appNavigator.back() })

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                ManageStaffSignInHero()
                SpaceVertical(24.dp)
                ManageStaffSignInFormSection(
                    account = formState.account,
                    password = formState.password,
                    onAccountChange = { viewModel.updateAccount(it) },
                    onPasswordChange = { viewModel.updatePassword(it) },
                    onSignInClick = { viewModel.signIn() }
                )
                SpaceVertical(16.dp)
//                ManageStaffSignInFooterVisual(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 24.dp, bottom = 24.dp)
//                )
            }
        }
    }
}

@Composable
private fun ManageStaffSignInMeshBackground(modifier: Modifier = Modifier) {
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
private fun ManageStaffSignInTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Colors.GrayF1F5F9)
            )
        }
        Text(
            text = stringResource(R.string.auth_manage_staff_title),
            color = Colors.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
private fun ManageStaffSignInHero() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Colors.Pink4DF425F4, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo_small),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }
        SpaceVertical(24.dp)
        Text(
            text = stringResource(R.string.app_name),
            color = Colors.GrayF1F5F9,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp,
            textAlign = TextAlign.Center
        )
        SpaceVertical(3.dp)
        Text(
            text = stringResource(R.string.auth_guest_brand_subtitle).uppercase(),
            color = Colors.Blue148,
            fontSize = 10.sp,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ManageStaffSignInFormSection(
    account: String,
    password: String,
    onAccountChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AppInputText(
            value = account,
            placeHolderRes = R.string.all_email_address,
            hintRes = R.string.auth_manage_staff_hint_work_email,
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = onAccountChange
        )
        AppInputText(
            value = password,
            placeHolderRes = R.string.all_password,
            hintRes = R.string.auth_guest_hint_password,
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            onValueChange = onPasswordChange
        )
        SpaceVertical(4.dp)
        AppButton(R.string.all_sign_in, onClick = onSignInClick)
//        Button(
//            onClick = onSignInClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//                .shadow(
//                    elevation = 16.dp,
//                    shape = RoundedCornerShape(28.dp),
//                    spotColor = Colors.Pink33F425F4,
//                    ambientColor = Colors.Pink0DF425F4
//                ),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Colors.Primary,
//                contentColor = Color.White
//            ),
//            elevation = ButtonDefaults.buttonElevation(
//                defaultElevation = 0.dp,
//                pressedElevation = 4.dp
//            )
//        ) {
//            Text(
//                text = stringResource(R.string.all_sign_in),
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Icon(
//                imageVector = Icons.Outlined.Login,
//                contentDescription = null,
//                modifier = Modifier.padding(start = 8.dp)
//            )
//        }

//        ManageStaffSignInEncryptedBadge(
//            modifier = Modifier.fillMaxWidth()
//        )
    }
}

@Composable
private fun ManageStaffSignInEncryptedBadge(modifier: Modifier = Modifier) {
    val badgeColor = Colors.Gray64748B
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = null,
                tint = badgeColor,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = stringResource(R.string.auth_manage_staff_encrypted_line),
                color = badgeColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun ManageStaffSignInFooterVisual(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageUrl = stringResource(R.string.auth_manage_staff_footer_image_url)
    Box(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .alpha(0.4f)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(400)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Primary.copy(alpha = 0.1f))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Colors.Dark120812
                        )
                    )
                )
        )
    }
}
