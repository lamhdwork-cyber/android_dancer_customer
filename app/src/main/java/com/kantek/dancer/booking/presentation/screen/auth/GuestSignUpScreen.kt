package com.kantek.dancer.booking.presentation.screen.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppViewModel
import com.kantek.dancer.booking.data.local.UserLocalSource
import com.kantek.dancer.booking.data.remote.api.UserApi
import com.kantek.dancer.booking.domain.extension.resourceError
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.domain.model.ui.user.SignUpForm
import com.kantek.dancer.booking.presentation.MainAct
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.launch
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppKeyboard
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.AppInputPhoneNumber
import com.kantek.dancer.booking.presentation.widget.AppInputText
import com.kantek.dancer.booking.presentation.widget.ApplyDarkEdgeToEdgeStatusBars
import com.kantek.dancer.booking.presentation.widget.LegalDisclaimerDialog
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestSignUpScreen(viewModel: SignUpVM = koinViewModel()) = ScopeProvider {
    ApplyDarkEdgeToEdgeStatusBars()

    val context = LocalContext.current
    val appNavigator = use<AppNavigator>()
    val formState by viewModel.formState.collectAsState()
    val termsAgreed by viewModel.termsAgreed.collectAsState()
    val success by viewModel.onSuccess.collectAsState()

    fun navigateToMain() {
        val intent = Intent(context, MainAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }


    var showLegalDisclaimerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(success) {
        if (success) {
            navigateToMain()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812)
    ) {
        GuestSignUpMeshBackground(Modifier.matchParentSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            GuestSignUpTopBar(onBack = { appNavigator.back() })

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 8.dp)
            ) {
                SpaceVertical(8.dp)
                GuestSignUpHero()
                SpaceVertical(20.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    AppInputText(
                        value = formState.firstName,
                        lightBackground = false,
                        placeHolderRes = R.string.all_first_name,
                        hintRes = R.string.auth_guest_sign_up_hint_first,
                        leadingIcon = Icons.Outlined.Person,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateFirstName(it) }
                    )
                    AppInputText(
                        value = formState.lastName,
                        lightBackground = false,
                        placeHolderRes = R.string.all_last_name,
                        hintRes = R.string.auth_guest_sign_up_hint_last,
                        leadingIcon = Icons.Outlined.Person,
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        onValueChange = { viewModel.updateLastName(it) }
                    )
                }
//                SpaceVertical(20.dp)
//                AppInputPhoneNumber(
//                    value = formState.phone,
//                    lightBackground = false,
//                    leadingIcon = Icons.Outlined.Smartphone,
//                    placeHolderRes = R.string.all_phone_number,
//                    onValueChange = { viewModel.updatePhone(it) }
//                )
                SpaceVertical(20.dp)
                AppInputText(
                    value = formState.email,
                    lightBackground = false,
                    placeHolderRes = R.string.auth_guest_label_email,
                    hintRes = R.string.auth_guest_hint_email,
                    leadingIcon = Icons.Outlined.Email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { viewModel.updateEmail(it) }
                )
                SpaceVertical(20.dp)
                AppInputText(
                    value = formState.password,
                    lightBackground = false,
                    placeHolderRes = R.string.all_password,
                    hintRes = R.string.auth_guest_hint_password,
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    onValueChange = { viewModel.updatePassword(it) }
                )
                SpaceVertical(20.dp)
                GuestSignUpTermsRow(
                    checked = termsAgreed,
                    onCheckedChange = { viewModel.updateLegalDisclaimer(it) },
                    onLegalLinkClick = { showLegalDisclaimerDialog = true }
                )
                SpaceVertical(16.dp)
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 8.dp, bottom = 24.dp)
            ) {
                GuestSignUpCreateAccountButton(onClick = { viewModel.signUp() })
                SpaceVertical(24.dp)
                GuestSignUpSignInFooter(onSignInClick = { appNavigator.back() })
            }
        }

        if (showLegalDisclaimerDialog) {
            LegalDisclaimerDialog(
                termsAgreed,
                onAgree = {
                    showLegalDisclaimerDialog = false
                    viewModel.updateLegalDisclaimer(true)
                },
                onDismiss = {
                    showLegalDisclaimerDialog = false
                }
            )
        }
    }
}

@Composable
private fun GuestSignUpMeshBackground(modifier: Modifier = Modifier) {
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
private fun GuestSignUpTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Colors.Pink33F425F4)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.auth_guest_sign_up_brand_line),
                color = Colors.Primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Text(
                text = stringResource(R.string.auth_guest_sign_up_title),
                color = Colors.GrayF1F5F9,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
private fun GuestSignUpHero() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.auth_guest_sign_up_hero_prefix),
                color = Colors.GrayF1F5F9,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = stringResource(R.string.auth_guest_sign_up_hero_elite),
                color = Colors.Primary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                letterSpacing = (-0.5).sp
            )
        }
        SpaceVertical(8.dp)
        Text(
            text = stringResource(R.string.auth_guest_sign_up_subtitle),
            color = Colors.Blue148,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GuestSignUpTermsRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onLegalLinkClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Colors.Primary,
                uncheckedColor = Colors.Gray6B7280,
                checkmarkColor = Color.White
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = stringResource(R.string.auth_guest_sign_up_terms_prefix),
                fontSize = 14.sp,
                color = Colors.Blue148
            )
            Text(
                text = stringResource(R.string.auth_guest_sign_up_terms_link),
                color = Colors.Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onLegalLinkClick() }
            )
            Text(
                text = stringResource(R.string.auth_guest_sign_up_terms_and),
                fontSize = 14.sp,
                color = Colors.Blue148
            )
            Text(
                text = stringResource(R.string.auth_guest_sign_up_privacy_link),
                color = Colors.Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onLegalLinkClick() }
            )
        }
    }
}

@Composable
private fun GuestSignUpCreateAccountButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Colors.Pink33F425F4,
                ambientColor = Colors.Pink0DF425F4
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Primary,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.auth_guest_sign_up_create_account),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun GuestSignUpSignInFooter(onSignInClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.auth_already_have_an_account),
            fontSize = 14.sp,
            color = Colors.Blue148
        )
        Text(
            text = stringResource(R.string.all_sign_in),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.Primary,
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { onSignInClick() }
        )
    }
}

class SignUpVM(
    private val appKeyboard: AppKeyboard,
    private val signUpRepo: SignUpRepo
) : AppViewModel() {

    private val _form = MutableStateFlow(SignUpForm())
    val formState: StateFlow<SignUpForm> = _form

    private val _termsAgreed = MutableStateFlow(false)
    val termsAgreed: StateFlow<Boolean> = _termsAgreed

    val onSuccess = MutableStateFlow(false)

    fun updateFirstName(it: String) {
        if (_form.value.firstName != it)
            _form.value = _form.value.copy(firstName = it)
    }

    fun updateLastName(it: String) {
        if (_form.value.lastName != it)
            _form.value = _form.value.copy(lastName = it)
    }

    fun updateEmail(it: String) {
        if (_form.value.email != it)
            _form.value = _form.value.copy(email = it)
    }

    fun updatePhone(it: String) {
        if (_form.value.phone != it)
            _form.value = _form.value.copy(phone = it)
    }

    fun updatePassword(it: String) {
        if (_form.value.password != it)
            _form.value = _form.value.copy(password = it)
    }

    fun updateLegalDisclaimer(it: Boolean) {
        if (_termsAgreed.value != it) _termsAgreed.value = it
    }

    fun signUp() = launch(loading, error) {
        appKeyboard.hide()
        _form.value.run {
            valid()
            if (!_termsAgreed.value) {
                resourceError(R.string.error_valid_legal_disclaimer)
            }
            signUpRepo(this)
            onSuccess.value = true
        }
    }
}

class SignUpRepo(
    private val userLocalSource: UserLocalSource,
    private val userApi: UserApi
) {
    suspend operator fun invoke(form: SignUpForm) {
        val body = form.copy(
            firstName = form.firstName.trim(),
            lastName = form.lastName.trim(),
            deviceToken = userLocalSource.getTokenPush()
        )
        userLocalSource.saveUserResponse(userApi.signUp(body).await())
    }
}
