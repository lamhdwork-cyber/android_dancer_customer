package com.kantek.dancer.booking.presentation.screen.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.viewmodel.BrowserVM
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.AppWebView
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutUsScreen(viewModel: BrowserVM = koinViewModel()) = ScopeProvider {
    val appNavigator = use<AppNavigator>(Scopes.App)
    var isLoading by remember { mutableStateOf(false) }
    val isLoadingApi by viewModel.customLoading.isLoading().collectAsState()

    val url by viewModel.aboutUs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAboutUs()
    }

    LaunchedEffect(isLoadingApi) {
        isLoading = isLoadingApi
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.account_about_us) { appNavigator.back() }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp),
        ) {
            if (url.isNotEmpty()) {
                AppWebView(
                    url = url,
                    Modifier.fillMaxSize(),
                    onLoading = { isLoading = it }
                )
            }
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}