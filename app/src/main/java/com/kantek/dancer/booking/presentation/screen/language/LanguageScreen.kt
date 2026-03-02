package com.kantek.dancer.booking.presentation.screen.language

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.MainAct
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.viewmodel.LanguageVM
import com.kantek.dancer.booking.presentation.widget.AppButton
import com.kantek.dancer.booking.presentation.widget.AppLazyColumn
import com.kantek.dancer.booking.presentation.widget.SpaceVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun LanguageScreen(
    isInApp: Boolean,
    viewModel: LanguageVM = koinViewModel()
) = ScopeProvider {
    val context = LocalContext.current
    val appNavigator = use<AppNavigator>(Scopes.App)
    val selectedLanguage = viewModel.selectedLanguage.collectAsState().value
    val languages by viewModel.items.collectAsState()
    val onContinueSuccess by viewModel.onContinueSuccess.collectAsState()
    val onChangeSuccess by viewModel.onChangeSuccess.collectAsState()

    LaunchedEffect(Unit) { viewModel.onFetch() }

    LaunchedEffect(onChangeSuccess) {
        if (onChangeSuccess.second) {
            (context as? Activity)?.recreate()
        }
        if (onChangeSuccess.first)
            appNavigator.back()
    }

    LaunchedEffect(onContinueSuccess) {
        if (onContinueSuccess) {
            val intent = Intent(context, MainAct::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpaceVertical(50.dp)
        Text(
            text = stringResource(R.string.lang_select),
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.lang_select_des),
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SpaceVertical(50.dp)
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color.Black.copy(alpha = 0.9f),
                    spotColor = Color.Black.copy(alpha = 0.3f)
                ),
        ) {
            AppLazyColumn(
                items = languages,
            ) { item, _, isLastItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.setLanguage(item.code) }
                        .background(Color.White)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(item.nameRes), modifier = Modifier.weight(1f))
                    if (selectedLanguage == item.code) {
                        Icon(
                            imageVector = Icons.Default.Check, contentDescription = "Selected",
                            tint = Colors.Primary
                        )
                    }
                }
                if (!isLastItem)
                    HorizontalDivider(color = Colors.Gray238)
            }
        }

        SpaceVertical(30.dp)
        if (!isInApp)
            AppButton(R.string.all_contiue) {
                viewModel.markForWelcome()
                viewModel.saveLanguage()
            }
        if (isInApp)
            AppButton(R.string.all_change_language) {
                viewModel.saveLanguage(false)
            }
    }
}

