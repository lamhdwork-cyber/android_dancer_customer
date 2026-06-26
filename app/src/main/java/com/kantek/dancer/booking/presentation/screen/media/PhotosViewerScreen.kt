package com.kantek.dancer.booking.presentation.screen.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.navigation.AppNavigator
import com.kantek.dancer.booking.presentation.theme.Colors
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import android.support.ui.widget.ZoomablePager

@Composable
fun PhotosViewerScreen(imageUrls: List<String>) = ScopeProvider {
    val appNavigator = use<AppNavigator>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_photo_viewer) { appNavigator.back() }

        ZoomablePager(
            imageUrls = imageUrls,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
    }
}