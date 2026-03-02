package com.kantek.dancer.booking.presentation.screen.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.domain.model.support.Scopes
import com.kantek.dancer.booking.presentation.extensions.ScopeProvider
import com.kantek.dancer.booking.presentation.extensions.use
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import com.kantek.dancer.booking.presentation.widget.ActionBarBackAndTitleView
import com.kantek.dancer.booking.presentation.widget.ZoomableAsyncImage

@Composable
fun PhotoViewerScreen(photoURL: String) = ScopeProvider {
    val appNavigator = use<AppNavigator>(Scopes.App)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionBarBackAndTitleView(R.string.top_bar_photo_viewer) { appNavigator.back() }
        ZoomableAsyncImage(
            imageUrl = photoURL,
            modifier = Modifier.clipToBounds()
        )
    }
}