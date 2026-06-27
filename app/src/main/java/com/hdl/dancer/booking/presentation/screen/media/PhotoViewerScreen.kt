package com.hdl.dancer.booking.presentation.screen.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hdl.dancer.booking.R
import com.hdl.dancer.booking.presentation.extensions.ScopeProvider
import com.hdl.dancer.booking.presentation.extensions.use
import com.hdl.dancer.booking.presentation.helper.AppNavigator
import com.hdl.dancer.booking.presentation.theme.Colors
import com.hdl.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.hdl.dancer.booking.presentation.widget.ZoomableAsyncImage

@Composable
fun PhotoViewerScreen(photoURL: String) = ScopeProvider {
    val appNavigator = use<AppNavigator>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Dark120812),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_photo_viewer) { appNavigator.back() }
        ZoomableAsyncImage(
            imageUrl = photoURL,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
    }
}